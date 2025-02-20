# Quaternions in Java

## Description
This Java implementation of **quaternions** allows for **3D rotations**. Their are basic **mathematical operations** too.

A quaternion is represented as:

\[
q = a + bi + cj + dk
\]

where **a** is the real part, and **b, c, d** are the imaginary components. These four values are the fields of the Quaternion class.

---

## References
Here are some useful resources for understanding quaternions:

- **Mathematical Background:**  
  [course on quaternions](https://math.univ-cotedazur.fr/~walter/L1_Arith/cours_H_v2.pdf)  
- **Video Explanation:**  
  [Quaternions for Rotations (YouTube)](https://www.youtube.com/watch?v=bKd2lPjl92c)  

---

## Installation
To use this **Quaternion** library in your Java project:

1. **Clone the repository:**
   ```sh
   git clone https://github.com/CamilleAnsel/Quaternions-Java.git
   cd Quaternions-Java
   
2. **Compile the class**
   ```sh
   javac Quaternion.java

3. **Use it in your project**. There is an Example.java script which shows how to use some features.
   ```sh
   javac Example.java
   java Example

4. **generate and check out the javadoc**. 
   ```sh
   javadoc -d doc Quaternion.java
   doc/index.html
