package cpg.util;


import static java.nio.charset.Charset.defaultCharset;
import static org.omg.spec.api4kp._20200801.AbstractCarrier.rep;
import static org.omg.spec.api4kp._20200801.taxonomy.krformat.snapshot.SerializationFormat.XML_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguageSeries.asEnum;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.snapshot.KnowledgeRepresentationLanguage.CMMN_1_1;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.snapshot.KnowledgeRepresentationLanguage.DMN_1_2;
import static org.omg.spec.api4kp._20200801.taxonomy.krlanguage.snapshot.KnowledgeRepresentationLanguage.Knowledge_Asset_Surrogate_2_0;
import static org.omg.spec.api4kp._20200801.taxonomy.parsinglevel._20200801.ParsingLevel.Serialized_Knowledge_Expression;

import cpg.covid19.ed.Main;
import edu.mayo.kmdp.kdcaci.knew.trisotech.TrisotechAssetRepository;
import edu.mayo.kmdp.kdcaci.knew.trisotech.preprocess.MetadataExtractor;
import edu.mayo.kmdp.kdcaci.knew.trisotech.preprocess.Weaver;
import edu.mayo.kmdp.language.parsers.cmmn.v1_1.CMMN11Parser;
import edu.mayo.kmdp.language.parsers.dmn.v1_2.DMN12Parser;
import edu.mayo.kmdp.language.parsers.surrogate.v2.Surrogate2Parser;
import edu.mayo.kmdp.trisotechwrapper.TrisotechWrapper;
import edu.mayo.kmdp.util.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import org.omg.spec.api4kp._20200801.AbstractCarrier;
import org.omg.spec.api4kp._20200801.api.terminology.v4.server.TermsApiInternal;
import org.omg.spec.api4kp._20200801.id.Pointer;
import org.omg.spec.api4kp._20200801.services.KnowledgeCarrier;
import org.omg.spec.api4kp._20200801.services.transrepresentation.ModelMIMECoder;
import org.omg.spec.api4kp._20200801.surrogate.KnowledgeAsset;
import org.omg.spec.api4kp._20200801.taxonomy.krlanguage.KnowledgeRepresentationLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/*
  Use this class to re-initialize the test models in the /resources folder

  Downloads the surrogate (native TT and KMDP standard) as well as the models
  (pre and post rewriting of the semantic annotations) to support testing
  of the different components
 */

@SpringBootApplication()
@PropertySource("/cpg-repo.properties")
@ComponentScan(
    basePackageClasses = {
        TrisotechWrapper.class,
        TrisotechAssetRepository.class,
        Weaver.class,
        MetadataExtractor.class
    })
public class CPGAssetDownloader {

  static final Logger logger = LoggerFactory.getLogger(CPGAssetDownloader.class);

  @Autowired
  TrisotechAssetRepository assetRepository;

  @Autowired
  TrisotechWrapper wrapper;


  @Bean
  public TermsApiInternal termsProvider() {
    return new CPGTermsServer();
  }

  @PostConstruct
  public void run() {
    logger.info("Preparing Asset Download");
    assetRepository.listKnowledgeAssets()
        .ifPresent(l -> l.forEach(assetPtr -> saveArtifacts(assetPtr, Main.bpmPath)));
  }

  protected void saveArtifacts(Pointer assetPtr, Path tgtFolder) {
    if (!Files.exists(tgtFolder)) {
      try {
        Files.createDirectories(tgtFolder);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    KnowledgeAsset surrogate
        = assetRepository
        .getKnowledgeAsset(assetPtr.getUuid(), assetPtr.getVersionTag())
        .orElseThrow(IllegalStateException::new);
    KnowledgeCarrier wovenModel
        = assetRepository
        .getKnowledgeAssetVersionCanonicalCarrier(assetPtr.getUuid(), assetPtr.getVersionTag())
        .orElseThrow(IllegalStateException::new);

    String name = surrogate.getName();
    String ext = getLanguageExtension(surrogate);

    logger.info("Persisting {} >> {}", name, tgtFolder);

    File surrFile = tgtFolder.resolve(name + ".surrogate" + ".xml").toFile();
    saveSurrogate(surrogate, surrFile);
    File modelFile = tgtFolder.resolve(name + ext + ".xml").toFile();
    saveModel(wovenModel, modelFile);
  }

  private void saveModel(KnowledgeCarrier wovenModel, File modelFile) {
    if (modelFile.getName().endsWith("cmmn.xml")) {
      new CMMN11Parser().applyLift(wovenModel,
          Serialized_Knowledge_Expression,
          ModelMIMECoder
              .encode(rep(CMMN_1_1, XML_1_1, defaultCharset())),
          null)
          .flatOpt(AbstractCarrier::asString)
          .ifPresent(str -> FileUtil
              .write(str, modelFile));
    } else if (modelFile.getName().endsWith("dmn.xml")) {
      new DMN12Parser().applyLift(wovenModel,
          Serialized_Knowledge_Expression,
          ModelMIMECoder
              .encode(rep(DMN_1_2, XML_1_1, defaultCharset())),
          null)
          .flatOpt(AbstractCarrier::asString)
          .ifPresent(str -> FileUtil
              .write(str, modelFile));
    }
  }

  private void saveSurrogate(KnowledgeAsset surrogate, File surrFile) {
    new Surrogate2Parser().applyLower(
        AbstractCarrier.ofAst(surrogate, rep(Knowledge_Asset_Surrogate_2_0)),
        Serialized_Knowledge_Expression,
        ModelMIMECoder
            .encode(rep(Knowledge_Asset_Surrogate_2_0, XML_1_1, defaultCharset())),
        null)
        .flatOpt(AbstractCarrier::asString)
        .ifPresent(
            str -> FileUtil.write(str, surrFile));
  }

  private String getLanguageExtension(KnowledgeAsset surrogate) {
    KnowledgeRepresentationLanguage lang = surrogate.getCarriers().get(0).getRepresentation()
        .getLanguage();
    switch (asEnum(lang)) {
      case CMMN_1_1:
        return ".cmmn";
      case DMN_1_2:
        return ".dmn";
      case BPMN_2_0:
        return ".bpmn";
      default:
        throw new IllegalStateException("Unrecognized language " + lang);
    }
  }


}
