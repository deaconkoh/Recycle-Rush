package collisionManager;

import com.badlogic.gdx.graphics.Pixmap;

public class PixelPerfectCollisionStrategy implements CollisionStrategy {
    @Override
    public boolean checkCollision(Collidable obj1, Collidable obj2) {
        float p1_left = obj1.getX() - obj1.getWidth() / 2;
        float p1_bottom = obj1.getY() - obj1.getHeight() / 2;
        float p1_right = obj1.getX() + obj1.getWidth() / 2;
        float p1_top = obj1.getY() + obj1.getHeight() / 2;

        float p2_left = obj2.getX() - obj2.getWidth() / 2;
        float p2_bottom = obj2.getY() - obj2.getHeight() / 2;
        float p2_right = obj2.getX() + obj2.getWidth() / 2;
        float p2_top = obj2.getY() + obj2.getHeight() / 2;

        float interLeft = Math.max(p1_left, p2_left);
        float interRight = Math.min(p1_right, p2_right);
        float interBottom = Math.max(p1_bottom, p2_bottom);
        float interTop = Math.min(p1_top, p2_top);

        int interWidth = (int)(interRight - interLeft);
        int interHeight = (int)(interTop - interBottom);

        if (interWidth <= 0 || interHeight <= 0) return false;

        Pixmap pixmap1 = obj1.getPixmap();
        Pixmap pixmap2 = obj2.getPixmap();

        if (pixmap1 == null || pixmap2 == null) return false;

        float scaleX1 = pixmap1.getWidth() / obj1.getWidth();
        float scaleY1 = pixmap1.getHeight() / obj1.getHeight();
        float scaleX2 = pixmap2.getWidth() / obj2.getWidth();
        float scaleY2 = pixmap2.getHeight() / obj2.getHeight();

        for (int i = 0; i < interWidth; i++) {
            for (int j = 0; j < interHeight; j++) {
                float worldX = interLeft + i;
                float worldY = interBottom + j;

                int texX1 = (int)((worldX - p1_left) * scaleX1);
                int texY1 = (int)((worldY - p1_bottom) * scaleY1);
                int texX2 = (int)((worldX - p2_left) * scaleX2);
                int texY2 = (int)((worldY - p2_bottom) * scaleY2);

                if (texX1 < 0 || texX1 >= pixmap1.getWidth() || texY1 < 0 || texY1 >= pixmap1.getHeight()) continue;
                if (texX2 < 0 || texX2 >= pixmap2.getWidth() || texY2 < 0 || texY2 >= pixmap2.getHeight()) continue;

                int pixel1 = pixmap1.getPixel(texX1, texY1);
                int pixel2 = pixmap2.getPixel(texX2, texY2);
                int alpha1 = (pixel1 >>> 24) & 0xff;
                int alpha2 = (pixel2 >>> 24) & 0xff;

                if (alpha1 > 0 && alpha2 > 0) return true;
            }
        }

        return false;
    }
}
