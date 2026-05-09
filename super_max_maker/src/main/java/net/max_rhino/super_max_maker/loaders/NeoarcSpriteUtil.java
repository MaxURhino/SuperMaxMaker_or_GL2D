package net.max_rhino.super_max_maker.loaders;

import net.max_rhino.gl2d.engine.math.Recti;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class NeoarcSpriteUtil {
    public static List<NeoarcSpriteInfo.Frame> getFrames(NeoarcSpriteInfo info, String name) {
        int id = -1;

        ListIterator<NeoarcSpriteInfo.NamedAnimation> it = info.getNamedAnimations().listIterator();

        while (it.hasNext()) {
            int i = it.nextIndex();
            NeoarcSpriteInfo.NamedAnimation namedAnimation = it.next();

            if (namedAnimation.getName().equals(name)) {
                id = i;
                break;
            }
        }

        if (id == -1) {
            throw new RuntimeException("Cannot find any animation with the name: " + name);
        }

        String[] frames = info.getNamedAnimations().get(id).getFrames().split(",");

        List<NeoarcSpriteInfo.Frame> framesList = new ArrayList<>();

        for (String frame : frames) {
            NeoarcSpriteInfo.Frame frameInfo = info.getFrames().get(Integer.parseInt(frame));
            framesList.add(frameInfo);
        }

        return framesList;
    }

    public static Recti convertRectToRecti(NeoarcSpriteInfo.Frame frame) {
        String[] rect = frame.getRect().split(" ");
        int x1 = Integer.parseInt(rect[0]);
        int y1 = Integer.parseInt(rect[1]);
        int x2 = Integer.parseInt(rect[2]);
        int y2 = Integer.parseInt(rect[3]);
        return new Recti(
                x1,
                y1,
                x2 - x1,
                y2 - y1 + 1
        );
    }

    public static Vector2i convertOffsetToVector2i(NeoarcSpriteInfo.Frame frame) {
        String[] offset = frame.getOffset().split(" ");
        int x = Integer.parseInt(offset[0]);
        int y = Integer.parseInt(offset[1]);
        return new Vector2i(x, y);
    }
}
