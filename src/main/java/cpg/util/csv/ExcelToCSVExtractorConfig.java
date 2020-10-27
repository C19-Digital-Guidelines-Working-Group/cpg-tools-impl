/**
 * Copyright © 2018 Mayo Clinic (RSTKNOWLEDGEMGMT@mayo.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpg.util.csv;

import cpg.util.csv.ExcelToCSVExtractorConfig.ExcelToCSVExtractorOptions;
import edu.mayo.kmdp.ConfigProperties;
import edu.mayo.kmdp.Opt;
import edu.mayo.kmdp.Option;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class ExcelToCSVExtractorConfig extends
    ConfigProperties<ExcelToCSVExtractorConfig, ExcelToCSVExtractorOptions> {

  private static final Properties DEFAULTS = defaulted(ExcelToCSVExtractorOptions.class);

  public ExcelToCSVExtractorConfig() {
    super(DEFAULTS);
  }

  @Override
  public ExcelToCSVExtractorOptions[] properties() {
    return ExcelToCSVExtractorOptions.values();
  }

  public enum ExcelToCSVExtractorOptions implements Option<ExcelToCSVExtractorOptions> {

    AS_TEXT(Opt.of("asText", "false", "", Boolean.class, false)),
    SEPARATOR(Opt.of("separator", ",", "", String.class, false)),
    SHEET(Opt.of("sheet", "0", "", Integer.class, false));

    private Opt<ExcelToCSVExtractorOptions> opt;

    ExcelToCSVExtractorOptions(Opt<ExcelToCSVExtractorOptions> opt) {
      this.opt = opt;
    }

    @Override
    public Opt<ExcelToCSVExtractorOptions> getOption() {
      return opt;
    }

  }
}
