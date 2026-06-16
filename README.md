# Restaurant Reservation System

A Java-based reservation management system that efficiently handles reservations, waitlists, and customer rewards programs using five core data structures.

---

## Overview

This application enables restaurant staff to:
- Create, view, and cancel customer reservations
- Process waitlisted customers in FIFO order
- Manage a rewards program with unique customer tracking
- Search for reservations by phone number in O(log n) time

---

## Data Structures Implemented

| Data Structure | Implementation | Purpose |
|----------------|----------------|---------|
| **LinkedList** | `recentReservations` | Stores reservations with most recent first; O(1) access to newest entries |
| **TreeMap** | `reservationsByDateTime` | Auto-sorts reservations chronologically for schedule viewing |
| **Queue** | `waitlistQueue` | FIFO structure ensuring fair customer service order |
| **HashSet** | `rewardsMembers` | Prevents duplicate customer enrollments in rewards program |
| **Binary Search Tree** | `phoneNumberBST` | Custom BST implementation providing O(log n) search, insert, delete |

---

## Technical Stack

- **Language:** Java (JDK 11+)
- **Paradigm:** Object-Oriented Programming
- **Collections:** Java Collections Framework
- **Custom Implementation:** Binary Search Tree from scratch



