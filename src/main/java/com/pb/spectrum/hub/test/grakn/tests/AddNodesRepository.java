package com.pb.spectrum.hub.test.grakn.tests;

import com.pb.spectrum.hub.test.grakn.GraknRepository;
import com.pb.spectrum.hub.test.harness.tests.add.AddNodesRepo;
import com.pb.spectrum.hub.test.model.DatasetConfig;
import com.pb.spectrum.hub.test.model.NodeRecord;

import java.util.Set;

public class AddNodesRepository extends GraknRepository implements AddNodesRepo
{
  public AddNodesRepository()//OrientGraph graph)
  {
    super(null);
  }

  @Override
  public void addNode(DatasetConfig config, NodeRecord nodeRecord) throws Exception {
    //try {

        StringBuilder sql = new StringBuilder("UPDATE "+nodeRecord.getName()+" SET ");

        Set<String> keySet = nodeRecord.getProperties().keySet();
        StringBuilder values = new StringBuilder();
        for(String key : keySet)
        {
            String valueString = key +" = :"+key;
            values.append(valueString).append(", ");
        }
        String valuesString = values.toString().replaceAll(", $", "");
        String finalQuery = "UPDATE "+nodeRecord.getName()+" SET "+valuesString.toString()+" UPSERT WHERE id = :id";
        //String finalQuery = "UPDATE "+nodeRecord.getName()+" SET "+valuesString.toString()+" UPSERT WHERE id = :id'"+nodeRecord.getProperties().get("id")+"'";
 //       graph.executeSql(finalQuery, nodeRecord.getProperties());
 //   }
   /* catch(ORecordDuplicatedException eRecordDuplicatedException)
    {
      eRecordDuplicatedException.printStackTrace();
    }
    catch(OValidationException oValidationException)
    {
      oValidationException.printStackTrace();
    }*/
  }
}
