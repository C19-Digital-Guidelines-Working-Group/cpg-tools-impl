package edu.mayo.kmdp.kdcaci.knew.asset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.SerializationFormatSeries.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.DMN_1_2;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel.ParsingLevelSeries.Abstract_Knowledge_Expression;

import edu.mayo.kmdp.language.parsers.dmn.v1_2.DMN12Parser;
import edu.mayo.kmdp.util.FileUtil;
import edu.mayo.kmdp.util.StreamUtil;
import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.JAXBElement;
import org.junit.jupiter.api.Test;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.Answer;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.omg.spec.api4kp._20200801.services.transrepresentation.ModelMIMECoder;
import org.omg.spec.dmn._20180521.model.TDefinitions;
import org.omg.spec.dmn._20180521.model.TInputData;

public class DMNConceptInspector {

  String basePath = "C:\\Users\\M123110\\Projects\\MEA\\cpg-tools-impl\\src\\test\\resources\\covidED";

  DMN12Parser parser = new DMN12Parser();

  Map<URI,String> declaredInputs = new HashMap<>();
  Map<String,Set<String>> usingModels = new HashMap<>();
  Map<String,Set<String>> modelToInputs = new HashMap<>();


  @Test
  void testDMNModels() {
    Answer<KnowledgeCarrier> parsedModels
        = loadDMNModels(basePath)
        .flatMap(kc -> parser.applyLift(kc, Abstract_Knowledge_Expression, ModelMIMECoder.encode(rep(DMN_1_2)),null));
    assertEquals(9, parsedModels.get().components().count());

    parsedModels.get().components()
        .map(x -> x.as(TDefinitions.class))
        .flatMap(StreamUtil::trimStream)
        .forEach(this::indexInputs);

    declaredInputs.forEach((key, value) -> System.out.println(value));

    System.out.println("\n\n");
    declaredInputs.forEach((key, value) -> System.out.println(value + "\t(" + key + ")"));

    System.out.println("\n\n");
    usingModels.entrySet().forEach(System.out::println);

    System.out.println("\n\n");
    modelToInputs.entrySet().forEach(System.out::println);

  }





  private void indexInputs(TDefinitions dmnModel) {
    dmnModel.getDrgElement().stream()
        .map(JAXBElement::getValue)
        .flatMap(StreamUtil.filterAs(TInputData.class))
        .forEach(input -> indexInput(input,dmnModel));
  }

  private void indexInput(TInputData input, TDefinitions dmnModel) {
    declaredInputs.put(
        URI.create(dmnModel.getNamespace() + "#" + input.getId()),
        input.getName());
    usingModels.computeIfAbsent(input.getName(),in -> new HashSet<>());
    modelToInputs.computeIfAbsent(dmnModel.getName(),in -> new HashSet<>());
    usingModels.get(input.getName()).add(dmnModel.getName());
    modelToInputs.get(dmnModel.getName()).add(input.getName());
  }

  private Answer<KnowledgeCarrier> loadDMNModels(String basePath) {
    List<KnowledgeCarrier> comps = Arrays.stream(new File(basePath)
        .listFiles(f -> f.getName().endsWith("dmn.xml")))
        .map(File::toURI)
        .map(FileUtil::readBytes)
        .flatMap(StreamUtil::trimStream)
        .map(bytes -> AbstractCarrier.of(bytes,rep(DMN_1_2,XML_1_1)))
        .collect(Collectors.toList());
    return Answer.of(AbstractCarrier.ofIdentifiableSet(comps));
  }

}
