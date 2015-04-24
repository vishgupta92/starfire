# Bayesian Network training tool: #

## 1. Objectives ##
The aim of this tool is to generate a BN from cases stored in an xml file, using the Smile library. An initial BN (in .xdsl or .net format) is needed to get the nodes and possible states.


## 2. Installation ##
This tool uses the jsmile library (http://genie.sis.pitt.edu/download/software.html). In order to work, put this library on your Java Library Path. It also uses the jdom library to read xml files (http://www.jdom.org).



## 3. Usage ##
### _3.1 Program usage_ ###
Firstly, put the xml file and the Bayesian network (.xdsl) file in the same folder. Then call the processTraining method in BNTrainingTool.java with the files path string as an argument. This will create a .data file with all the cases from the training file. Then, this file is passed to the smile library that generates the Bayesian network, which is stored in a subfolder called newBN in the path given with “NEW\_” as a prefix. Also, a confidence.txt file is created. The confidence value is estimated by counting the lines in the .data file and adding the initial confidence from the training file. You can also check the log file saved in the same path.

### _3.2 XML File_ ###
The format of the xml has to be similar as shown in the example. As many cases as desired can be written in this file:
```
<cases confidence="50">
    <case weight = "5">
        <nodes>
            <node id = "A" type=”TRAINING_LIST” value="1::2::3::4"/>
            <node id = "B" type=”TRAINING_NOT” value="2::3"/>
            <node id = "C" value=" "/>
        </nodes>
    </case>
    <case>
        <nodes>
            <node id = "A" value="2"/>
            <node id = "B" value=" "/>
            <node id = "C" value=" 3"/>
        </nodes>
    </case>
</cases>
```

-**Confidence**: This value sets the confidence of the initial BN. Set to 0 to use only the cases written in this file.

-**Weight**: Sets the weight of the case. Higher weight means higher probability of that case.

-**Nodes ID**: The id of the node, the same as the BN.

-**Nodes Type**: The type of the value.

-**TRAINING\_LIST**: If there are multiple values for this node.

-**TRAINING\_NOT**: Generates one case for each value of the node from the BN except the ones specified in the value attribute. Supports multiple values.

-**Nodes Value**: The values for the specific case. Separate multiple values with a double colon “::” .

### _3.3 Bayesian Network_ ###
The Bayesian network has to be saved in .xdsl or .net format.

## 4. Comments ##
The tool works correctly but in order to obtain a good Bayesian network, some testing is needed.