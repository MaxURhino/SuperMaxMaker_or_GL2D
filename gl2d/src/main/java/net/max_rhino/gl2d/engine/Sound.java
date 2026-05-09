package net.max_rhino.gl2d.engine;

import net.max_rhino.gl2d.GL2D;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.openal.AL10.*;

public class Sound implements Disposable {
    public static List<String> SOUNDS_FORMATS = new ArrayList<>();
    public static List<Sound> SOUNDS = new ArrayList<>();

    @Override
    public void dispose() {
        alDeleteSources(source);
        alDeleteBuffers(buffer);

        SOUNDS.remove(this.listId);
    }

    public enum SoundFormat {
        WAV((data, channels, sampleRate, totalFrames) -> {
            data.order(ByteOrder.LITTLE_ENDIAN);
            if (data.getInt(0) != 0x46464952)
                throw new IllegalArgumentException("Not a valid WAV file: missing RIFF header");
            if (data.getInt(8) != 0x45564157)
                throw new IllegalArgumentException("Not a valid WAV file: missing WAVE identifier");
            int numChannels  = Short.toUnsignedInt(data.getShort(22));
            int rate         = data.getInt(24);
            int bitsPerSample = Short.toUnsignedInt(data.getShort(34));

            if (bitsPerSample != 16)
                throw new UnsupportedOperationException("Only 16-bit PCM WAV is supported, got: " + bitsPerSample);
            int dataSize = data.getInt(40);
            int frameCount = dataSize / (numChannels * 2);

            channels.put(0, numChannels);
            sampleRate.put(0, rate);
            totalFrames.put(0, frameCount);

            data.position(44);
            ByteBuffer pcmSlice = data.slice();
            pcmSlice.order(ByteOrder.LITTLE_ENDIAN);
            pcmSlice.limit(dataSize);

            ShortBuffer pcm = BufferUtils.createShortBuffer(dataSize / 2);
            pcm.put(pcmSlice.asShortBuffer());
            pcm.flip();

            return pcm;
        }),
        OGG((data, channels, sampleRate, totalFrames) -> STBVorbis.stb_vorbis_decode_memory(data, channels, sampleRate)),
        MP3((data, channels, sampleRate, totalFrames) -> {
            throw new GL2D.MethodUnsupportedException("This method is not supported");
        }),
        AAC((data, channels, sampleRate, totalFrames) -> {
            throw new GL2D.MethodUnsupportedException("This method is not supported");
        }),
        FLAC((data, channels, sampleRate, totalFrames) -> {
            throw new GL2D.MethodUnsupportedException("This method is not supported");
        }),
        M4A((data, channels, sampleRate, totalFrames) -> {
            throw new GL2D.MethodUnsupportedException("This method is not supported");
        });

        private final SoundFactory factory;

        SoundFormat(SoundFactory factory) {
            this.factory = factory;
            SOUNDS_FORMATS.add(this.toString());
        }

        public SoundFactory getFactory() {
            return factory;
        }

        @Override
        public String toString() {
            return "." + this.name().toLowerCase();
        }

        public String getAsFile(String path) {
            return path + this;
        }

        public static String getAsFile(SoundFormat format, String path) {
            return format.getAsFile(path);
        }
    }

    @FunctionalInterface
    public interface SoundFactory {
        ShortBuffer apply(ByteBuffer data, IntBuffer channels, IntBuffer sampleRate, IntBuffer totalFrames);
    }

    @Nullable
    public static SoundFormat findASoundFormatForAPath(Path path) {
        for (SoundFormat format : SoundFormat.values()) {
            if (Files.exists(Path.of(path.toString() + format))) {
                return format;
            }
        }
        return null;
    }

    public static boolean hasOperationalFileFormat(Path path) {
        for (SoundFormat format : SoundFormat.values()) {
            if (path.endsWith(format.toString())) {
                return true;
            }
        }
        return false;
    }

    private int buffer;
    private int source;

    private final int listId;

    public Sound(Path path) {
        String stringPath = path.toString();
        if (!hasOperationalFileFormat(path)) {
            @Nullable SoundFormat temp = findASoundFormatForAPath(path);
            if (temp != null) {
                stringPath += temp;
            }
        }
        SoundFormat soundFormat = Objects.requireNonNull(findASoundFormatForAPath(path));
        try {
            byte[] bytes = Files.readAllBytes(Path.of(stringPath));

            ByteBuffer data = BufferUtils.createByteBuffer(bytes.length);
            data.put(bytes).flip();

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer channels = stack.mallocInt(1);
                IntBuffer sampleRate = stack.mallocInt(1);
                IntBuffer totalFrames = stack.mallocInt(1);

                ShortBuffer pcm = soundFormat.factory.apply(data, channels, sampleRate, totalFrames);

                if (pcm == null) {
                    throw new RuntimeException("Failed to load sound: " + stringPath);
                }

                int format = channels.get(0) == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;

                this.buffer = alGenBuffers();
                alBufferData(buffer, format, pcm, sampleRate.get(0));

                if (soundFormat == SoundFormat.OGG)
                    MemoryUtil.memFree(pcm);

                this.source = alGenSources();
                alSourcei(source, AL_BUFFER, buffer);
            }

            listId = SOUNDS.size();
            SOUNDS.add(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Sound play() {
        alSourcePlay(source);
        return this;
    }

    public Sound stop() {
        alSourceStop(source);
        return this;
    }

    public Sound pause() {
        alSourcePause(source);
        return this;
    }

    public Sound rewind() {
        alSourceRewind(source);
        return this;
    }

    public Sound replay() {
        return this.stop().rewind().play();
    }

    public float getPlaybackPos() {
        return alGetSourcef(source, 0x1024);
    }

    public Sound setPlaybackPos(float pos) {
        alSourcef(source, 0x1024, pos);
        return this;
    }

    public Sound setLooping(boolean looping) {
        alSourcei(source, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
        return this;
    }
}
