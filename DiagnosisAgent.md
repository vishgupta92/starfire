# Objectives #

The aim of this agent is start a diagnosis when a sympton is received. Once the diagnosis has finished, the agent select an action to perform.

# Components #
This agent is composed by

> ## Java Clases ##
    * _LoadOWLPlan_ : this class load the .owl archive.
    * _MonitorRequestProcessorPlan_ : this class throw a sympton when the transimission is not ok.
    * _ObservationManagerPlan_ : this class manage the observations
    * _RtcpSearchPlan_ : this class search for a rtcp session
    * _SymptonManagerPlan_ : this class manage the symptons
    * _VlcLauncherPlan_ : this class launch the VLC with the predefined options.
    * _DiagnosisLoopPlan_ : this class analizes hypothesis an observations in order to perform the action with the highest expected benefit.

> ## XML File ##
    * _Diagnosis.agent_ : this agent uses all the plans in order to search for an rtcp session, find an error in the transmission, recive the sympton and perform and action using hypothesis and observations.


# Process #
First, the agent load de .owl file, when the file is load, the agent search for a RTCP session, once the session has been established, the agent open VLC with the direction of the video. Every second the agent ask for a message wich contains the jitter and the packet lost rate. If there is a problem in the transmision the agent throw a sympton and the diagnosis loop will start. In the diagnosis loop, first the class load the bayesian network. Next, the class extract the hypothesis and observations nodes. Select the state of each observation and assign a probabilistic value for each hypothesis. Then the agent calculeta de CDF for the hypothesis and assign the expected benefit for each action. Finally the agent execute de rule engine an dsleect an action to perform