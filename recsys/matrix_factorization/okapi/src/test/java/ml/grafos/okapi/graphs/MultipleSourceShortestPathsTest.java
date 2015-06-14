/**
 * Copyright 2014 Grafos.ml
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
package test.java.ml.grafos.okapi.graphs;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import ml.grafos.okapi.common.Parameters;
import ml.grafos.okapi.io.formats.LongFloatTextEdgeInputFormat;

import org.apache.giraph.conf.GiraphConfiguration;
import org.apache.giraph.io.formats.IdWithValueTextOutputFormat;
import org.apache.giraph.utils.InternalVertexRunner;
import org.junit.Test;

public class MultipleSourceShortestPathsTest {

  @Test
  public void test() {
    String[] graph = { 
        "1 2 1.0",
        "2 1 1.0",
        "1 3 1.0",
        "3 1 1.0",
        "2 3 2.0",
        "3 2 2.0",
        "3 4 2.0",
        "4 3 2.0",
        "3 5 1.0",
        "5 3 1.0",
        "4 5 1.0",
        "5 4 1.0"
    };

    GiraphConfiguration conf = new GiraphConfiguration();
    conf.setComputationClass(MultipleSourceShortestPaths.InitSources.class);
    conf.setMasterComputeClass(MultipleSourceShortestPaths.MasterCompute.class);
    conf.setEdgeInputFormatClass(LongFloatTextEdgeInputFormat.class);
    conf.setVertexOutputFormatClass(IdWithValueTextOutputFormat.class);
    conf.setFloat(MultipleSourceShortestPaths.SOURCES_FRACTION, 0.4f);
    conf.setLong(Parameters.RANDOM_SEED.getKey(), 0);
    Iterable<String> results;
    try {
      results = InternalVertexRunner.run(conf, null, graph);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Exception occurred");
      return;
    }
    List<String> res = new LinkedList<String>();
    for (String string : results) {
      res.add(string);
      System.out.println(string);
    }
    Assert.assertEquals(5, res.size());
  }

}
