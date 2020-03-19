package com.pb.spectrum.hub.test.grakn.tests;

import com.pb.spectrum.hub.test.harness.tests.addbatch.AddNodesBatchRepo;
import com.pb.spectrum.hub.test.model.DatasetConfig;
import com.pb.spectrum.hub.test.model.NodeRecord;

import java.util.List;
import java.util.Map;

//import org.apache.tinkerpop.gremlin.structure.Graph;

public class AddNodesBatchRepository extends AddNodesRepository implements AddNodesBatchRepo {

    public AddNodesBatchRepository()//OrientGraph graph)
    {
        super();
    }

    @Override
    public void addNodes(Map<String, DatasetConfig> datasets, List<NodeRecord> records) throws Exception {

        for (NodeRecord record : records) {
            addNode(datasets.get(record.getName()), record);
        }
        //commit();

    }
}
