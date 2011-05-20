#!/bin/bash
Saludo="Inserting dependencies in maven local repository: "
echo $Saludo


mvn install:install-file -DgroupId=guess -DartifactId=jpf -Dversion=1.5 -Dpackaging=jar -Dfile=jpf-1.5.jar
mvn install:install-file -DpomFile=pom.xml -Dpackaging=jar -Dfile=unbbayes-4.0.0.jar


mvn install:install-file -DgroupId=edu.stanford -DartifactId=protege -Dversion=3.4.6 -Dpackaging=jar -Dfile=protege.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=lax -Dversion=3.4.6 -Dpackaging=jar -Dfile=lax.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=looks -Dversion=3.4.6 -Dpackaging=jar -Dfile=looks.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=unicode_panel -Dversion=3.4.6 -Dpackaging=jar -Dfile=unicode_panel.jar


mvn install:install-file -DgroupId=edu.stanford -DartifactId=protege-owl -Dversion=3.4.6 -Dpackaging=jar -Dfile=protege-owl.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=antlr -Dversion=2.7.5 -Dpackaging=jar -Dfile=antlr-2.7.5.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=antlr-runtime -Dversion=3.1.3 -Dpackaging=jar -Dfile=antlr-runtime-3.1.3.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=arq -Dversion=3.4.6 -Dpackaging=jar -Dfile=arq.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=arq-extra -Dversion=3.4.6 -Dpackaging=jar -Dfile=arq-extra.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=axis -Dversion=3.4.6 -Dpackaging=jar -Dfile=axis.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=commons-discovery -Dversion=0.4 -Dpackaging=jar -Dfile=commons-discovery-0.4.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=commons-lang -Dversion=2.0 -Dpackaging=jar -Dfile=commons-lang-2.0.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=commons-logging -Dversion=1.1.1 -Dpackaging=jar -Dfile=commons-logging-1.1.1.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=concurrent -Dversion=3.4.6 -Dpackaging=jar -Dfile=concurrent.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=drools-api -Dversion=5.1.1 -Dpackaging=jar -Dfile=drools-api-5.1.1.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=drools-compiler -Dversion=5.1.1 -Dpackaging=jar -Dfile=drools-compiler-5.1.1.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=drools-core -Dversion=5.1.1 -Dpackaging=jar -Dfile=drools-core-5.1.1.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=edtftpj -Dversion=1.5.2 -Dpackaging=jar -Dfile=edtftpj-1.5.2.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=ekitspell -Dversion=3.4.6 -Dpackaging=jar -Dfile=ekitspell.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=icu4j_3_4 -Dversion=3.4.6 -Dpackaging=jar -Dfile=icu4j_3_4.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=iri -Dversion=3.4.6 -Dpackaging=jar -Dfile=iri.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=jcalendar -Dversion=3.4.6 -Dpackaging=jar -Dfile=jcalendar.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=jdom -Dversion=3.4.6 -Dpackaging=jar -Dfile=jdom.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=jena -Dversion=3.4.6 -Dpackaging=jar -Dfile=jena.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=jep -Dversion=2.4.0 -Dpackaging=jar -Dfile=jep-2.4.0.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=json -Dversion=3.4.6 -Dpackaging=jar -Dfile=json.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=junit -Dversion=3.4.6 -Dpackaging=jar -Dfile=junit.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=kazuki -Dversion=3.4.6 -Dpackaging=jar -Dfile=kazuki.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=log4j -Dversion=1.2.12 -Dpackaging=jar -Dfile=log4j-1.2.12.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=mvel2 -Dversion=2.0.16 -Dpackaging=jar -Dfile=mvel2-2.0.16.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=orphanNodesAlg -Dversion=3.4.6 -Dpackaging=jar -Dfile=orphanNodesAlg.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=owlsyntax -Dversion=3.4.6 -Dpackaging=jar -Dfile=owlsyntax.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=stax-api -Dversion=1.0 -Dpackaging=jar -Dfile=stax-api-1.0.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=swingx -Dversion=1.0 -Dpackaging=jar -Dfile=swingx-1.0.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=swrl-drools-bridge -Dversion=3.4.6 -Dpackaging=jar -Dfile=swrl-drools-bridge.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=swrl-jess-bridge -Dversion=3.4.6 -Dpackaging=jar -Dfile=swrl-jess-bridge.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=wstx-asl -Dversion=3.0.0 -Dpackaging=jar -Dfile=wstx-asl-3.0.0.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=xercesImpl -Dversion=3.4.6 -Dpackaging=jar -Dfile=xercesImpl.jar
mvn install:install-file -DgroupId=edu.stanford -DartifactId=xml-apis -Dversion=3.4.6 -Dpackaging=jar -Dfile=xml-apis.jar

echo "Waiting 20s to let you read..."
sleep 20
echo "Ready?"
