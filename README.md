WWII-Enigma
===========

This is a Java-Based simulator of the WWII-era enigma encryption machine. It is an exercise that demonstrates the
strength of Java's OOP properties.

This program consists of 2 main classes: Machine and Rotor. Machine utilises a combination of rotors and rotor
extending subclasses to convert a string to text. 

To initialize this machine, call make then call java enigma.Main
The first line of any input must be a configuration line of format "* B Beta I II III AXLE". These fields are variable
the roman numerals represent which rotors you are selecting from the pool, while the B, Beta and AXLE fields represent
the configurations of the fixed rotors and reflectors. 

A full list of possible configurations can be found in the PermutationData class.

