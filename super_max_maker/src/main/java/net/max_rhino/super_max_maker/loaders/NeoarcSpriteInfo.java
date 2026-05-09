package net.max_rhino.super_max_maker.loaders;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NeoarcSpriteInfo {
    private List<Frame> Frames;
    private List<NamedAnimation> NamedAnimations;
    private String Version;
    private String SubPositions;

    // Getters

    public List<Frame> getFrames() {
        return Frames;
    }

    public List<NamedAnimation> getNamedAnimations() {
        return NamedAnimations;
    }

    public String getVersion() {
        return Version;
    }

    public String getSubPositions() {
        return SubPositions;
    }

    // Setters

    public void setFrames(List<Frame> Frames) {
        this.Frames = Frames;
    }

    public void setNamedAnimations(List<NamedAnimation> NamedAnimations) {
        this.NamedAnimations = NamedAnimations;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public void setSubPositions(String SubPositions) {
        this.SubPositions = SubPositions;
    }

    public static class Frame {
        private Integer FaceAngle;
        private Integer FaceDirection;
        private String Offset;
        private String Rect;
        @Nullable
        private List<Object> SubPositions = List.of();

        // Getters

        public Integer getFaceAngle() {
            return FaceAngle;
        }

        public Integer getFaceDirection() {
            return FaceDirection;
        }

        public String getOffset() {
            return Offset;
        }

        public String getRect() {
            return Rect;
        }

        @Nullable
        public List<Object> getSubPositions() {
            return SubPositions;
        }

        // Setters

        public void setFaceAngle(Integer FaceAngle) {
            this.FaceAngle = FaceAngle;
        }

        public void setFaceDirection(Integer FaceDirection) {
            this.FaceDirection = FaceDirection;
        }

        public void setOffset(String Offset) {
            this.Offset = Offset;
        }

        public void setRect(String Rect) {
            this.Rect = Rect;
        }

        public void setSubPositions(@Nullable List<Object> SubPositions) {
            this.SubPositions = SubPositions;
        }
    }

    public static class NamedAnimation {
        @Nullable
        private Integer Delay = 0;
        private String Frames;
        private String Name;

        // Getters

        @Nullable
        public Integer getDelay() {
            return Delay;
        }

        public String getName() {
            return Name;
        }

        public String getFrames() {
            return Frames;
        }

        // Setters

        public void setDelay(@Nullable Integer Delay) {
            this.Delay = Delay;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setFrames(String Frames) {
            this.Frames = Frames;
        }
    }
}
