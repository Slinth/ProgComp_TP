package tp.model;

public class Operation {
    private double a;
    private double b;
    private String opt;
    private double res;

    public Operation() {
    }

    public Operation(double a, double b, String opt) {
        this.a = a;
        this.b = b;
        this.opt = opt;
        this.resoudre();
    }

    public void resoudre() {
        if (opt.equals("+")) {
            this.res = a + b;
        } else if (opt.equals("-")) {
            this.res = a - b;
        } else if (opt.equals("*")) {
            this.res = a * b;
        } else {
            this.res = a / b;
        }
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public void setRes(double res) {
        this.res = res;
    }

    public double getRes() {
        return res;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "a=" + a +
                ", b=" + b +
                ", opt=" + opt +
                ", res=" + res +
                '}';
    }
}
