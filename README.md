# Social Media Visualization (Java, GUI + Console)

This project analyzes social media influencer data from CSV files, computes engagement rates, 
and visualizes the results. Originally developed as a group project, later refactored to run 
independently with pure Java (no external course libraries required).

## Features
- **CSV Parsing**: Reads influencer data (month, channel, topic, likes, comments, views, etc.)
- **Engagement Calculation**: Supports both Traditional formula and By-Reach formula
- **Sorting**: Custom LinkedList with insertion sort and comparator classes
- **Console Runner**: Outputs engagement rates sorted by channel or by engagement
- **GUI Version**: Pure Java Swing interface with bar charts and toggle controls (month, sort, formula)

## Engagement Formulas
- **Traditional**: `(comments + likes) / followers * 100`
- **By Reach**: `(comments + likes) / views * 100`

## Requirements
- Java 17+  

