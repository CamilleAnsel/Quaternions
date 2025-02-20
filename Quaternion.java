/**
 * Represents a quaternion, a mathematical structure used in 3D rotations and complex number extensions.
 * A quaternion is expressed as: q = a + bi + cj + dk, where a, b, c, and d are real numbers.
 */
public class Quaternion {
    // Fields representing the four components of the quaternion
    private double a, b, c, d;

    // Constructors
    /**
     * Default constructor. Creates a quaternion with all components set to zero.
     */
    public Quaternion() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    /**
     * Constructor to initialize a quaternion with specific values.
     * @param a The real part of the quaternion.
     * @param b The first imaginary component.
     * @param c The second imaginary component.
     * @param d The third imaginary component.
     */
    public Quaternion(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Constructor to initialize a unit quaternion representing a rotation.
     * @param angle The angle of rotation in radians.
     * @param axis A 3D unit vector [x, y, z] representing the rotation axis.
     */
    public Quaternion(double angle, double[] axis) {
        if (axis.length != 3) {
            throw new IllegalArgumentException("Axis must be a 3-element array.");
        }

        // Normalize the axis
        double norm = Math.sqrt(axis[0] * axis[0] + axis[1] * axis[1] + axis[2] * axis[2]);
        if (norm == 0) {
            throw new IllegalArgumentException("Rotation axis cannot be the zero vector.");
        }

        double halfAngle = angle / 2.0;
        double sinHalf = Math.sin(halfAngle);

        this.a = Math.cos(halfAngle);
        this.b = sinHalf * (axis[0] / norm);
        this.c = sinHalf * (axis[1] / norm);
        this.d = sinHalf * (axis[2] / norm);
    }

    // Getters
    /**
     * Returns the real part of the quaternion.
     * @return The real component (a) of the quaternion.
     */
    public double Re() {
        return a;
    }

    /**
     * Return the imaginary part of the quaternion.
     * @return A 3-element array [b, c, d] representing the imaginary components.
     */
    public double[] Im() {
      return new double[]{b, c, d};
    }


    // Setters
    /**
     * Sets the real part of the quaternion.
     * @param real The new real component of the quaternion.
     */
    public void setRe(double real) {
        this.a = real;
    }

    /**
     * Sets the imaginary part of the quaternion.
     * @param imaginary A 3-element array [b, c, d] representing the imaginary components.
     * @throws IllegalArgumentException if the array does not have exactly 3 elements.
     */
    public void setIm(double[] imaginary) {
        if (imaginary.length != 3) {
            throw new IllegalArgumentException("Imaginary part must be a 3-element array.");
        }

        this.b = imaginary[0];
        this.c = imaginary[1];
        this.d = imaginary[2];
    }

    // ToString
    /**
     * Returns a string representation of the quaternion.
     * @return A string in the format "a + bi + cj + dk".
     */
    @Override
    public String toString() {
        return String.format("%.3f + %.3fi + %.3fj + %.3fk", a, b, c, d);
    }

    // Checker

    /**
     * Checks if this Quaternion is a unit quaternion (norm = 1).
     * It must take in count the unaccuracy of double type.
     * @return true if the Quaternion is unitary, false otherwise.
     */
    public boolean isUnitQuaternion() {
      double epsilon = 1e-10; // tolerance for floating-point precision
      return Math.abs(norm() - 1.0) < epsilon;
    }

    // Methods
    /**
     * Adds another quaternion to this one.
     * @param q The quaternion to add.
     */
    public void add(Quaternion q) {
        this.a += q.a;
        this.b += q.b;
        this.c += q.c;
        this.d += q.d;
    }

    /**
     * Multiplies this quaternion by a scalar value.
     * @param r The scalar value to multiply by.
     */
    public void multiplyBy(double r) {
        this.a *= r;
        this.b *= r;
        this.c *= r;
        this.d *= r;
    }

    /**
     * Multiplies this quaternion by another quaternion.
     * @param q The quaternion to multiply by.
     */
    public void multiplyBy(Quaternion q) {
        double newA = a * q.a - b * q.b - c * q.c - d * q.d;
        double newB = a * q.b + b * q.a + c * q.d - d * q.c;
        double newC = a * q.c - b * q.d + c * q.a + d * q.b;
        double newD = a * q.d + b * q.c - c * q.b + d * q.a;

        this.a = newA;
        this.b = newB;
        this.c = newC;
        this.d = newD;
    }

    /**
     * Computes the determinant (or norm squared) of the quaternion.
     * @return The determinant value, computed as a² + b² + c² + d².
     */
    public double determinant() {
        return a * a + b * b + c * c + d * d;
    }

    /**
     * Computes the norm (magnitude) of the quaternion.
     * @return The norm of the quaternion.
     */
    public double norm() {
        return Math.sqrt(a * a + b * b + c * c + d * d);
    }

    /**
     * Normalizes this quaternion to make it a unit quaternion.
     */
    public void normalize() {
        double norm = this.norm();
        if (norm == 0) {
            throw new ArithmeticException("Cannot normalize a zero quaternion.");
        }
        this.a /= norm;
        this.b /= norm;
        this.c /= norm;
        this.d /= norm;
    }

    /**
     * Computes the conjugate of the quaternion.
     * @return A new quaternion representing the conjugate (a, -b, -c, -d).
     */
    public Quaternion conj() {
        return new Quaternion(a, -b, -c, -d);
    }
    
    /**
     * Compute the rotation of a 3D vector using this Quaternion.
     * The formula is v'= q*v*q^(-1) with q this Quaternion and v' the rotated vector.
     * @param vector A 3D vector represented as an array [x,y,z].
     * @throws IllegalArgumentException if the array does not have exactly 3 elements.
     * @return The rotated vector as an array [x',y',z'].
     */
    public double[] rotate(double[] vector) {
      if (vector.length != 3){
        throw new IllegalArgumentException("Vector must be a 3-element array.");
      }
      
      Quaternion v = new Quaternion(0,vector[0], vector[1], vector[2]);
      Quaternion qInversed;
      if (this.isUnitQuaternion()){
          qInversed = this.conj(); // inverse of unitary quaternion is equal to it's conjugated.
      } else {
          qInversed = Quaternion.inverseOf(this);
      }

      // Compute the rotation by multiplying Quaternions
      Quaternion vRotated = Quaternion.multiplication(this, v);
      vRotated.multiplyBy(qInversed);
      
      // Extract and return the rotated vector
      return new double[]{vRotated.b, vRotated.c, vRotated.d};
    }

    // Static methods
    /**
     * Computes the sum of two quaternions.
     * @param q1 The first quaternion.
     * @param q2 The second quaternion.
     * @return A new quaternion representing the sum of q1 and q2.
     */
    public static Quaternion sum(Quaternion q1, Quaternion q2) {
        return new Quaternion(q1.a + q2.a, q1.b + q2.b, q1.c + q2.c, q1.d + q2.d);
    }

    /**
     * Computes the multiplication of a quaternion by a scalar.
     * @param r The scalar value.
     * @param q The quaternion to be scaled.
     * @return A new quaternion representing the scaled quaternion.
     */
    public static Quaternion multiplicationByScalar(double r, Quaternion q) {
        return new Quaternion(r * q.a, r * q.b, r * q.c, r * q.d);
    }


    /**
     * Computes the product of two quaternions.
     * @param q1 The first quaternion.
     * @param q2 The second quaternion.
     * @return A new quaternion representing the product of q1 and q2.
     */
    public static Quaternion multiplication(Quaternion q1, Quaternion q2) {
        return new Quaternion(
            q1.a * q2.a - q1.b * q2.b - q1.c * q2.c - q1.d * q2.d,
            q1.a * q2.b + q1.b * q2.a + q1.c * q2.d - q1.d * q2.c,
            q1.a * q2.c - q1.b * q2.d + q1.c * q2.a + q1.d * q2.b,
            q1.a * q2.d + q1.b * q2.c - q1.c * q2.b + q1.d * q2.a
        );
    }

    /**
     * Computes the inverse of a quaternion, if it has a nonzero determinant.
     * @param q The quaternion to invert.
     * @return A new quaternion representing the inverse of q.
     * @throws ArithmeticException if the quaternion has a determinant of zero.
     */
    public static Quaternion inverseOf(Quaternion q) {
        double normSquared = q.determinant(); // Equivalent to q.norm()^2
        if (normSquared == 0) throw new ArithmeticException("Cannot invert a quaternion with zero norm.");
        return multiplicationByScalar(1.0 / normSquared, q.conj());
    }


}
