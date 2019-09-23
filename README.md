# SortingCompetitionMaterials2018

Materials for UMM CSci 3501 "Algorithms and computability" 2019 sorting competition.

# Table of contents
* [Goal of the competition](#goal)
* [The data](#data)
* [How is the data generated](#generating)
* [String Feature Background](#stringbackground)
* [How do you need to sort the data](#sortingRules)
* [Setup for sorting](#setup)
* [Submision deadlines](#deadlines)
* [Scoring](#scoring)
* [System specs](#specs)
* [Results of the first preliminary round](#round1)
* [Results of the final competition](#final)
* [Presentations](#presentation)


## Goal of the competition <a name="goal"></a>

The Sorting Competition is a multi-lab exercise on developing the fastest sorting algorithm for a given type of data. By "fast" we mean the actual running time and not the Big-Theta approximation. The solutions are developed in Java and will be ran on a single processor.

## The data  <a name="data"></a>

Data for this sorting competition consists of standard UTF-8 characters stored in a text file.  Each entry appears on its own line.  An entry will be in one of three forms:

* A **pure fraction:** such as `4/5`
* A **mixed fraction:** such as `123 99/178`
* A **decimal expression** such as `137.63`

```
4/5
123 99/178
0.1378
```

A fraction need not be proper, nor must it be in lowest form.   For mixed fractions and pure fractions it is possible to have different representation of the same number.  The conflicts are resolved as:

# How is the data generated <a name="generating"></a>

Not all the expresion types are equally likely and there are rules about how expressions of different types are generated.


### Distribution of the expression types

The expression types are generated with the following frequencies:

1. Approximately one fifth of the values will be decimal expressions. 
2. Approximately two fifths of the values will be mixed fractions
3. Approximately one fifth of the values will be pure fractions


### decimal expressions

The whole number portion of a decimal expression will be a value between 0 and 5 digits long.  The length will be uniformly distributed.  Except for the first digit in the expression, even valued digits will be twice as likely as odd valued digits.  For the first digit, all values between 1 and 9 will be equally likely.

The decimal portion of the expression will be a value between 1 and 10 digits long.  The length will be unformly distributed.  In each position, even digits will be twice as likely as odd digits.

### Pure fraction

The numerator and the denominator will be uniformly distributed values from 1 to 9999999999.  Upon generation there is a ten percent chance that a "fraction pair" will be generated.  In these circumstances the original fraction is written to the file and then a random multiplier between 2 and 100 will be applied to both numerator and denominator to generate a second fraction that will become the next entry in the list.

### Mixed fractions

The whole number will be uniformly selected from the integers 1 to 9999999999 (inclusive).  The fraction portion follows the pure fraction generation rules except without the possibility of a "fraction pair" being produced.

### The data files

The generated values are written into a data file one per line. Sample data files are: [data1.txt](data1.txt) (1000 elements), [data2.txt](data2.txt) (1000 elements), and [data3.txt](data3.txt) (10000 elements). 

## How do you need to sort the data <a name="sortingRules"></a>

Sorting is done first by value.  If two expressions have the same value then the following rules are followed:

* Decimal expression
* Mixed fraction
* Pure fraction

For mixed fractions and pure fractions it is possible to have different representation of the same number.  The conflicts are resolved as:

**pure fractions:**  

A pure fraction is always one number followed by a `/` and then a second number.  If two pure fractions represent the same value, then the one with the **smallest numerator** goes first.  Therefore the following are in order:

```
8/5
16/10
32/20
```

**mixed fraction:**
first: The mixed fraction with the smallest while number goes first
second: If the two whole numbers are the same then the one with the smallest numerator goes first (same as the *pure fraction rule*)

Hence the following mixed fractions are in the proper order:

```
1 8/5
1 6/10
2 3/5
2 6/10
```

The file [Group0.java](src/Group0.java) provides a Comparator that implements this comparison and provides some tests. Please consult it as needed. However, note that this not an optimized implementation, and you should think of a way to make it much faster. 

Once the data is sorted, it is written out to the output file, also one number per line, in the increasing order (according to the comparison given above). The files [out1.txt](out1.txt), [out2.txt](out2.txt), and [out3.txt](out3.txt) have the results of sorting for the three given data files. 

## Setup for sorting <a name="setup"></a>

The file [Group0.java](src/Group0.java) provides a template for the setup for your solution. Your class will be called `GroupN`, where `N` is the group number that is assigned to your group. The template class runs the sorting method once before the timing for the [JVM warmup](https://www.ibm.com/developerworks/library/j-jtp12214/index.html). It also pauses for 10ms before the actual test to let any leftover I/O or garbage collection to finish. Since the warmup and the actual sorting are done on the same array (for no reason other than simplicity), the array is cloned from the same input data. 

The data reading, the array cloning, the warmup sorting, and writing out the output are all outside of the timed portion of the method, and thus do not affect the total time. 

You may not use any global variables that depend on your data. You may, however, have global constants that are initialized to fixed values (no computation!) before the data is being read and stay the same throughout the run. These constants may be arrays of no more than 1000 `long` numberss or equivalent amount of memory. For instance, if you are storing an array of objects that contain two `long` fields, you can only have 500 of them. We consider one `long` to be the same as two `int` numbers, so you can store an array of 2000 `int` numbers.  
If in doubt about specific cases, please discuss with me. 

The method in the [Group0.java](src/Group0.java) files that you may modify is the `sort` method. It must take the array of strings. The return type of the method can be what it is now, which is the same as the parameter type `String []`, or it can be a different array type. If you are sorting in-place, i.e. the sorted result is in the same array, then you can just return a reference to that array, as my prototype method does, or make your sorting method `void`. If you are returning a different type of an array, the following rules have to be followed:
* Your `sort` method return type needs to be changed to whatever  array you are returning, and consequently the type of `sorted` array in `main` needs to be changed. 
* Your return type has to be an array (not an array list!) and it has to have the same number of elements as the original array. 
* You need to supply a method to write out your result into a file. The file has to be exactly the same as in the prototype implementation; they will be compared using `diff` system command. 

If you are not changing the return type, you don't need to modify anything other than `sort` method. 

Even though you are not modifying anything other than the `sort` method, you still need to submit your entire class: copy the template, rename the Java class to your group number, and change the`sort` method. You may use supplementary classes, just don't forget to submit them. Make sure to add your names in comments when you submit. 

Your program must print **the only value**, which is the **time** (as it currently does). Any other printed output disqualifies your submission. If you use test prints, make sure to turn them off for submission. 

**Important:** if the sorting times may be too small to distinguish groups based on just one run of the sorting, so I may loop over the sorting section multiple times. If this is the case, I will let you know no later than a day after the preliminary competition and will modify `Group0` file accordingly.  

## Submision deadlines <a name="deadlines"></a>

See Canvas for the deadlines of this competition (if you're not in the class I'll send you a copy via email).

*Thursday, Nov 7th* (subject to change) in the lab is the *final* competition. All source code is posted immediately after that. Those in class will have their names revealed, others may choose to remain anonymous (but the code will still be posted). 

## Scoring <a name="scoring"></a>

The programs are tested on a few (between 1 and 3) data sets. For each data set each group's program is run three times, the median value counts. The groups are ordered by their median score for each data file and assigned places, from 1 to N. 

The final score is given by the sum of places for all data sets. If the sum of places is equal for two groups, the sum of median times for all the runs resolves the tie. So if one group was first for one data set and third for the other one (2 sets total being run), it scored better than a group that was third for the first data set and second for the other. However, if one group was first for the first set and third for the other one, and the second group was second in both, the sum of times determines which one of them won since the sum of places is the same (1 + 3 = 2 + 2). 

If a program has a compilation or a runtime error, doesn't sort correctly, or prints anything other than the total time in milliseconds, it gets a penalty of 1000000ms for that run. 

## System specs <a name="specs"></a>

The language used is Java 8 (as installed in the CSci lab). It's ran on a single CPU core.  

I will post a script for running this program (with a correctness check and all), but for now a couple of things to know: run your program out of `/tmp` directory to avoid overhead of communications with the file server, and pin your program to a single core, i.e. run it like this:
``taskset -c 0 java GroupN``

<!--
## Results of the first preliminary round <a name="round1"></a>

This round will (*to be announced*)

The results of the first preliminary round will be posted in the folder [round1/bin](round1/bin). The folder has all the `.class` files. Groups that didn't have a submission (or their submission didn't compile or would go into an infinite loop) show up as errors. 

Each data file was ran three times for each group, and the median result was used for scoring. 

The data files were: [prelim1.txt](round1/bin/prelim1.txt) (700 numbers) and [prelim2.txt](round1/bin/prelim2.txt) (400 numbers). The correct output is in files [outRun1Group0.txt](round1/bin/outRun1Group0.txt) and [outRun2Group0.txt](round1/bin/outRun2Group0.txt).

The files [results1.txt](round1/bin/results1.txt) and [results2.txt](round1/bin/results2.txt) have the complete timing results for the two data sets. The file [scoreboard.txt](round1/bin/scoreboard.txt) has the places that each team got.  

The ruby script [run_all.rb](round1/bin/run_all.rb) was used to run the programs. If you want to reproduce the results or try them on a different set, so the following:
* Create a directory in `/tmp` directory on a lab machine. 
* Copy the entire `bin` folder from github into that directory. 
* Remove the output files, results1.txt, results2.txt, and the scoreboard.txt. 
* If you want to run the programs on different data sets, call your data files `prelim1.txt` and 'prelim2.txt` and copy them into the same folder - or copy then by different names, abnd then open the  script and change the files in the `inFileNames`. 
* Type `taskset -c 0 ruby run_all.rb` to run the script. 

## Results of the competition <a name="final"></a>

Congratulations to everybody who contributed!  We had some great competition.  Thanks to everybody who participated.  You can look at the scoreboard in round2/bin for more exact details on how the scoring went.  The only out-of-place example comes from an entry that was submitted as a JAR file and had to be run separately... it's therefore, not in the scoreboard file.  The results, in order of achievement:

* group 15 (not in the class): 
* group 8 (Nick Plucker, Ethan Uphoff)
* group 6 (Liam Koehler, Charlot Shaw)
* group 2 (Mathew Munns, Ahnaf Prio)
* group 10 (Nick Bushway, Leonid Scott)
* group 9 (John Hoff, Abenezer Monjor)
* group 13 (David Chong, Jubair Hassan)
* group 7 (Ethan Hamer, Avery Koranda)
* group 0 (reference)
* group 11 (Ariel Cordes, Sungjae Park)
* group 5 (Yutaro Miyata, Sam Score)
* Standalone (not in class):
* group 1 didn't sort correctly (Dexter An, John Schonenbaum)
* group 3 didn't sort correctly (Colin Rabe, Kai Zang)
* group 4 didn't sort correctly (Hunter Welch, Laly Xiong)
* group 14 didn't sort correctly (Spencer Hammerstein, Xuejiao Yang)
* group 12 didn't sort correctly (Vipul Sharma)

-->
## Presentations <a name="presentation"></a>

The presentations date will be in December (we will adjust this date when the time gets closer). Each group needs to submit a set of 4-5 pdf slides by 11:59pm the day before the presentation; you will have 3-4 minutes to present. Both group members must be a part of a presentation. The presentation must have:

* Your names (first names ok since this will be on github) and group number,
* Your results of the competition (overall place, times, correctness). If there were any correctness concerns, they need to be addressed.
* Big-picture description of your algorithm and data representation (what sorting have you used?
what were you storing? Precomputing?)
* The theoretical worst case efficiency and expected efficiency. For instance, insertion sort in theory has a quadratic efficiency, but if you are using it only on almost-sorted data, it's close to linear.
* The most intersting features of your algorithm.
* What worked, what didn't, what you would've done differently.

Be prepared to answer questions. 

## Final write-up <a name="writeup"></a>

*details will be posted here*