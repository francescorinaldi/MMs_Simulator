# MMs_Simulator_Changelog

MMs Simulator

### 1. Abstract

Our project consisted of a restyling of the Marco Scoppetta’s **Java Simulation Tool** that allows users to simulate the architecture and the functioning of a queuing system.

This simulator is composed of various classes, derived both from the queuing theory and from the monitoring of techniques, each one with a specific task. They’re analyzed in the details in the Marco Scoppetta’s thesis: "[QoS-driven techniques for the management of cloud resources adaptations](https://www.politesi.polimi.it/bitstream/10589/106601/3/Tesi_completa.pdf)".

In order to improve the user experience during the execution of this tool and to maximize the potential of the software, we carefully worked on the code to give, for example, the user the possibility to _change the settings_ and make the most of it.

Finally, we pledged to unlock the _portability_ of the program, by providing an executable version of the Simulator, runnable both on Mac and on Windows.

In the following chapters, we will explain in the details for each improvement what issues we bumped into and how we provided a solution.

### 2. Parametrization

As we announced in the Abstract, the first main modification to the Simulation Tool was intended to enable the user to change settings, in order to guarantee a more flexible and adaptable usage of the tool.

For this reason we first created these two classes: "**Settings**" and "**Advanced Settings**".

We will use the first one to set the **Booting Time** [default value: _900.0_], the **Deactivation Timeout** [default value: _3600.0_], the **Hysteresis** [default value: _3_] and the number of **Iterations** [default value: _1_].

On the other hand, the second one will be useful to change the following advanced settings:
* _maxReconf_ : maximum number of Re-configurations
* _minReconf_ : minimum number of Re-configurations
* _totReconf_ : total number of Re-configurations
* _maxAwt_: maximum Awt
* _minAwt_: minimum Awt
* _totAwt_: total Awt
* _maxAvailability_: maximum Availability
* _minAvailability_: minimum Availability
* _totAvailability_: total Availability
* _maxPeak_: maximum value for the Peak
* _minPeak_: minimum value for the Peak
* _totPeak_: total value of the Peak
* _maxCost_: maximum Cost
* _minCost_: minimum Cost
* _totCost_: total Cost

This is the UML of the new classes created.

![alt tag](https://writelatex.s3.amazonaws.com/zxsthwgxrzdb/uploads/1535/7036415/1.png)

To let the user change all the settings we mentioned above, we created other two classes: "**MMs\_Simulator\_GUI**" and "**Advanced\_Settings\_GUI**".

In the first one we will create and manage a JFrame with 4 fields, one for each variable specified above, and 3 buttons:

* *Ok*: set the current settings displayed, and start the simulation;
* *Advanced Settings*: set the current settings displayed, and start the Advanced Settings GUI before starting the simulation;
* *Cancel*: interrupt the program by calling the *exit* method in class Runtime.

With the second one we can run a similar JFrame, a little bit bigger, with 15 fields, one for each of the advanced settings specified above, and just 2 buttons:
* *Ok*: set the current advanced settings displayed, and start the simulation;
* *Cancel*: interrupt the program by calling the *exit* method in class Runtime.

The particularity of these fields is that we created a "personalized" JFormattedTextField for each of them, paying attention to differentiate the **decimal format** and the **integer format**.

Even if the user will commit some typos or any kind of errors typing data, the program will immediately correct them: it will accept just numeric strings, promptly codified in **double** or **int** format.

After that, in order to manage the correct execution of this two GUIs and of the Simulation and to guarantee the sequentiality of all these actions, we built a class **Start** that took the place of the "**Entry Point**" class. *Start.main* has the task of manage three different threads: *askSettings*, *askAdvancedSettings* and *startSimulation*.

These three threads are related to the classes mentioned above in this way:
* *askSettings*: its aim is to ask user the general settings of the tool; to do that, it creates a new instance of MMs\_Simulator\_GUI and execute its main function;
* *askAdvancedSettings*: its aim is to ask user the advanced settings of the tool; to do that, it creates a new instance of Advanced\_Settings\_GUI and execute its main function;
* *startSimulation*: its aim is to start the simulation; to do that, it creates a new instance of **Simulation** (the class took the place of the "**Repetita Iuvant**" class) and execute its main function with the new settings just established by the user.


![alt tag](https://writelatex.s3.amazonaws.com/zxsthwgxrzdb/uploads/1552/7036659/1.jpg)


The choice to execute different threads is also due to the incompatibility of *JFrame* and *Dynamic Graph*: otherwise it wouldn't have been possible for us to execute them in sequence.

### 3. Creation of the Jar Executable

Once we achieved these goals, we decided to export the MMs Simulator Tool into a single executable file. In this way the distribution of the software would be much easier compared to the distribution of the source code to compile and run.

For this reason we focused on the creation of a multi-platform executable file, that will permit the user to use it on any machine without the need for a specific operating system.

The main disadvantages were related to the use of external files in the source code ("**vmOnDemand\_eu-west-1\_linux\_.xml**", "**vmReserved\_eu-west-1\_linux\_c1-xlarge.xml**" and "**traccia10seconds.txt**"). These files were localized on local addresses, and for this reason it was not possible to execute the program on machines where the simulation folder (with all the above-mentioned files) was not present.

The first solution was just putting the Jar created in the same folder with the .txt and .xml files, but it would be uncomfortable for the user, and it was for sure a non-professional solution.

For this reason we tried to integrate into the jar also these resources. To do it, we needed to modify the code in order to properly let the program have access to these files.

The original code in the "**XMLparser**" class and in the "**Manager**" class was using the Java File methods to open the two .xml files and the .txt file, but that didn't allow us to reach our goal: using the files is the best way while programming in Java, but it is the worst option in terms of ease of Java application deployment.

The usage of *absolute* file-names in the code didn't allow us to obtain a portable and disk-position independent function: using *relative* file-names seemed a better alternative, but they are resolved relative to the JVM's current directory. This directory setting depends on the details of the JVM's launch process, which can be obfuscated by startup shell scripts.

<p align="center">
  <img src = https://writelatex.s3.amazonaws.com/zxsthwgxrzdb/uploads/333/7017036/1.jpg>
</p>

This is why we used the abstract class **InputStream**: in this way we could take advantage from the method "**getResourceAsStream**". This method allowed us to obtain every files as a *stream*: a sequence of data that the program uses as input to read data from a source, one item at a time.

### 4. Conclusion

The aim of the project was to work on a Java Simulator Tool built by one of our colleagues three years ago, modifying it in order to enlarge the possibility to use it and to maximize the various advantages deriving from it usage.

It was also a challenge for us to fiddle with some code we didn't write by ourself and that we neither followed during the creation: a fundamental challenge that put to the test our capacities accrued gradually over the years, crucial for our future.

It's our hope to have completed our assignment in the most precise and professional way.

