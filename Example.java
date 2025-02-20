
/**
 * Example usage of the Quaternion class.
 * Demonstrates quaternion creation, rotation, multiplication, and inversion.
 */
public class Example {
    public static void main(String[] args) {
        // Creating quaternions
        Quaternion q1 = new Quaternion(1, 2, 3, 4);
        Quaternion q2 = new Quaternion(2, -1, 0, 3);

        System.out.println("Quaternion q1: " + q1);
        System.out.println("Quaternion q2: " + q2);

        // Checking if a quaternion is unitary
        System.out.println("Is q1 unitary? " + q1.isUnitQuaternion());

        // Normalizing q1
        q1.normalize();
        System.out.println("Normalized q1: " + q1);
        System.out.println("Is q1 unitary now? " + q1.isUnitQuaternion());

        // Quaternion multiplication
        Quaternion qProduct = Quaternion.multiplication(q1, q2);
        System.out.println("q1 * q2 = " + qProduct);

        // Inverting a quaternion
        Quaternion qInverse = Quaternion.inverseOf(q1);
        System.out.println("Inverse of q1: " + qInverse);

        // Creating a quaternion for rotation (90° around Z-axis)
        double angle = Math.PI / 2; // 90 degrees in radians
        double[] axis = {0, 0, 1};  // Rotation around Z-axis
        Quaternion qRotation = new Quaternion(angle, axis);
        System.out.println("Rotation Quaternion (90° around Z): " + qRotation);

        // Rotating a 3D vector
        double[] vector = {1, 0, 0}; // Original vector (X-axis)
        double[] rotatedVector = qRotation.rotate(vector);
        System.out.println("Rotated Vector: [" + rotatedVector[0] + ", " + rotatedVector[1] + ", " + rotatedVector[2] + "]");

        // Chaining multiple rotations
        double angle2 = Math.PI / 2; // Another 90-degree rotation
        double[] axis2 = {0, 1, 0};  // Rotation around Y-axis
        Quaternion qRotation2 = new Quaternion(angle2, axis2);
        Quaternion combinedRotation = Quaternion.multiplication(qRotation2, qRotation);

        double[] fullyRotatedVector = combinedRotation.rotate(vector);
        System.out.println("Vector after two rotations: [" + fullyRotatedVector[0] + ", " + fullyRotatedVector[1] + ", " + fullyRotatedVector[2] + "]");
    }
}
