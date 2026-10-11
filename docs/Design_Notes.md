# Design Notes

## Why ArrayList instead of an array?

An array has a fixed size that must be chosen up front. We do not know how many students,
courses or enrollments will be added, so an array would either waste space or run out.
An `ArrayList` grows automatically when we call `add()`, and it gives us ready-made
methods such as `add`, `remove`, `size` and `isEmpty`, so the code is shorter and easier to read.

## Where did we use static members and why?

- `IdGenerator` has static counters (`studentIdCounter`, `courseIdCounter`, `enrollmentIdCounter`)
  and static methods (`getNextStudentId()` etc.). IDs must be unique across the whole program,
  so the counter belongs to the class, not to any single object.
- `InputValidator` has only static helper methods. It needs no data of its own, so there is
  no reason to create an object just to call `isEmpty()` or `parseInt()`.
- `Main` uses static fields and methods because `main` itself is static and runs before any object exists.
- `AppConstants` and `MenuOptions` use `static final` fields for fixed values, so we avoid
  typing the same text or number in many places.

## Where did we use inheritance and what did we gain?

`Person` is the base class holding `id`, `firstName`, `lastName` and `email`.
`Student` and `Trainer` extend it.

- **Less duplicated code:** the shared fields, getters and setters are written once in `Person`.
- **`super` in constructors:** `Student` and `Trainer` call `super(...)` to set the shared fields.
- **Method overriding / polymorphism:** `getDisplayName()` is defined in `Person` and overridden in
  `Student` (adds the batch) and `Trainer` (adds the expertise). Calling `getDisplayName()` on a
  `Person` variable runs the version that belongs to the real object.

## Other design choices

- **Layers:** `Main` only shows menus and reads input. Services hold the rules (validation,
  "no duplicate enrollment", "inactive students cannot enroll"). Repositories only store data.
  Entities only hold data. Each class has one job.
- **Soft delete:** students and courses are deactivated (`active = false`) instead of removed,
  so their enrollment history is not lost. `removeStudent` exists in the service but is not used by the menu.
- **Exceptions:** `EntityNotFoundException` is used when an ID does not exist.
  `InvalidInputException` is used for bad input and broken rules. `Main` catches both and prints a
  friendly message, so the program never crashes on bad input.
- **Status as a String:** enrollment status is `"ACTIVE"`, `"COMPLETED"` or `"CANCELLED"`, kept as
  constants in `AppConstants` to avoid typing mistakes.
