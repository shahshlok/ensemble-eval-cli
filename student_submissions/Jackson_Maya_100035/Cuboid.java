public class Cuboid {
    private double length;
    private double width;
    private double height;
    private String color;

    public Cuboid(double l, double w, double h, String color) {
        length = l;
        width = w;
        height = h;
        this.color = color;
    }

    public Cuboid() {
        length = 1;
        width = 1;
        height = 1;
        color = "white";
    }

    public double getL() {
        return length;
    }

    public double getW() {
        return width;
    }

    public double getH() {
        return height;
    }

    public String getColor() {
        return color;
    }

    public double getSurfaceArea() {
        return 2 * (length * width + length * height);
    }

    public double getVolume() {
        return length * width * height;
    }

    public void displayInfo() {
        System.out.println("Color: " + color);
        System.out.println("l=" + length + ", w=" + width + ", h=" + height);
        System.out.println("Surface area: " + getSurfaceArea());
        System.out.println("Volume: " + getVolume());
    }

    public static void main(String[] args) {
        Cuboid defaultCuboid = new Cuboid();
        Cuboid secondCuboid = new Cuboid(8, 3.5, 5.9, "green");

        defaultCuboid.displayInfo();
        secondCuboid.displayInfo();
    }
}

