# JVM Basics

## JDK, JRE and JVM

- **JVM (Java Virtual Machine):** the program that actually runs Java bytecode. It turns bytecode
  into instructions the computer's processor understands.
- **JRE (Java Runtime Environment):** the JVM plus the standard Java libraries (like `ArrayList` and `Scanner`).
  It is what you need to *run* a Java program.
- **JDK (Java Development Kit):** the JRE plus development tools such as `javac`, the compiler.
  It is what you need to *write and compile* a Java program.

In short: **JDK ⊃ JRE ⊃ JVM**.

## What is bytecode?

When we run `javac Main.java`, the compiler does not produce machine code for one specific computer.
It produces **bytecode**, stored in a `.class` file. Bytecode is an in-between format that
only the JVM understands.

## Write once, run anywhere

We compile our code once into bytecode. That same `.class` file can run on Windows, Linux or macOS,
as long as a JVM is installed there. Each operating system has its own JVM that knows how to
run the same bytecode on that system, so the programmer does not need to rewrite or recompile the program for each one.
