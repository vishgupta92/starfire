# Overview #
A good fault diagnosis depends on a reliable BN. Even though the initials BN are made manually
by service operators, it's difficult to make them work accurately in every HAN. In order to improve
the diagnosis, the Knowledge Agent, which is the responsible for learning and updating the
diagnosis Bayesian knowledge, is introduced.



## 1.1 Self-learning functionality ##

The properties file, SelfLearning.properties, has to be configured for each HAN.

Self learning steps:

1.OSGI interface agents send diagnosis operation reports to a storage agent,
which is responsible for their storage in a database.

2.The knowledge agent starts the self learning process periodically (it can be
also started manually by a graphic interface). When launched, accesses the DB and
checks for reports. If any found, the expectation-maximization algorithm is started
with the initial BN and confidence file. It's necessary to have a confidence value
associated to each bayesian network.

3.When the new values are learnt, the knowledge agent distribute them to all
the diagnosis agents



# Configurable Parametres #

− It's important to set the paths to your configuration files properly. The path to
the files .properties (SelfLearning and SelfLearningDB) has to be specified in the
_target.xml file. The paths to the bayesian networks directory have to set up in
SelfLearning.properties._

− In order to self learn periodically, set DO\_SELF\_LEARNING to YES (in
SelfLearning.properties)

− The period can also be changed in the properties file (at least, it has to have time to
finish the process before it starts again).

− **IMPORTANT**: The bayesian network must have the same name as the scene\_id.



# ANNEX I: To-Do List #

− In each self learn process, just use the new validated reports.

− Instead of using always the initial BN, use the previous one for further learning
process.



# ANNEX II: Smile Library #

The learning process is perform by the jsmile library. In order to work, the .jar has to be
in magneto/lib folder and the libjsmile.so has to be in the java library path (in ubuntu,
/usr/lib/jvm/jdk1.6.0\_22/jre/lib/ ).