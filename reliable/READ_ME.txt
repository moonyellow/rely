This txt describe what is the files we submitted.

******************************
The root directory
******************************

MainTest.class is the class file with our scheduler code injected.

BenchSharedObjectRecord.class is the class file used to record the execution sequence, it is already with record code injected, once user executes this class file(rename and place this class file to the place it should be), the system will generate a record file documenting all the information needed to replay

BenchSharedObjectReplay.class is the class file with replay code injected, it utilized the record file, the user can then recreate the execute sequence recorded.

******************************
The adapter Package
******************************
.
├── RecordClassAdaptor.java
├── RecordMethodAdaptor.java
├── ReplayClassAdaptor.java
├── ReplayMethodAdaptor.java
├── SchedulerClassAdapter.java
└── SchedulerMethodAdapter.java

RecordClassAdaptor & RecordMethodAdaptor is used to transform the original class file into the recordable class file using ASM framework.

ReplayClassAdaptor & ReplayMethodAdaptor is used to inject the code needed to replay into the original class file.

SchedulerClassAdapter & SchedulerMethodAdapter is used to inject scheduler related code.

*******************************
The microbench Package
*******************************
.
├── BenchSharedObject.java
└── BenchThread.java

These two class is the micro-benchmark we used to examine our program. In this benchmark, two thread concurrently choosing one of the five random shared variable to increment, so each time the value of the five shared variables will be different.

*******************************
The recorder Package
*******************************
.
├── RandomTable.java
├── Recorder.java
├── RecordTable.java
└── RecordThread.java

As the name imply, this package implements the JDETER recorder

*******************************
The replayer Package
*******************************
.
└── Replayer.java

As the name imply, this package implements the JDETER replayer

*******************************
The scheduler Package
*******************************
.
└── Scheduler.java

As the name imply, this package implements the JDETER Scheduler


