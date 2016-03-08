package kz.ef.art.graphics;

class CeFire extends ComponentElement {

    public CeFire() {
        super(new AImage(Texture.FIRE));
    }

    public CeFire(int x, int y) {
        super(new AImage(Texture.FIRE));
        setLocation(x, y);
    }

}
