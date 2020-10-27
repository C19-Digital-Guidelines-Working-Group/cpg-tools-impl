package edu.mayo.kmdp.kdcaci.knew.asset;

import edu.mayo.kmdp.kdcaci.knew.trisotech.TrisotechAssetRepository;
import edu.mayo.kmdp.kdcaci.knew.trisotech.preprocess.MetadataExtractor;
import edu.mayo.kmdp.kdcaci.knew.trisotech.preprocess.Weaver;
import edu.mayo.kmdp.trisotechwrapper.TrisotechWrapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackageClasses = {
        TrisotechWrapper.class,
        TrisotechAssetRepository.class,
        Weaver.class,
        MetadataExtractor.class,
        })
public class CPGTestConfig {

}
