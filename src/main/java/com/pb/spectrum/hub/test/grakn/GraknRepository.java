package com.pb.spectrum.hub.test.grakn;

import com.pb.spectrum.hub.test.model.DatasetConfig;
import com.pb.spectrum.hub.test.model.EdgeConfig;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class GraknRepository {

        //protected Connection con;


        public GraknRepository(Connection conn) {

        }

        public void initialize(Collection<DatasetConfig> datasets) throws Exception {
        /*Set<DatasetConfig> edgeDataSets = new HashSet<DatasetConfig>();
        for (DatasetConfig ds : datasets) {
            if(ds instanceof EdgeConfig) {
                edgeDataSets.add(ds);
                continue;
            }
            initializeDataset(ds, Boolean.FALSE);
        }
        for(DatasetConfig dsEdge : edgeDataSets) {
            initializeDataset(dsEdge, Boolean.TRUE);
        }*/
        }

        private void initializeDataset(DatasetConfig ds, boolean edgeLoop) throws Exception {
            startTransaction();
            if (edgeLoop) {
                //CREATE UNDIRECTED EDGE friendship (FROM person, TO person, connect_day DATETIME)
                EdgeConfig edge = (EdgeConfig)ds;
                Collection<EdgeConfig.Endpoints> endpointCollection = edge.getEndpoints();
                String fromName, toName = "";
                Iterator endpointIterator = endpointCollection.iterator();

            /*for(EdgeConfig.Endpoints endpoint : endpointIterator)
            {

            }*/

                //endpointCollection.iterator().forEachRemaining(endpoints -> {endpoints.getFromName()});

                //String edge = new StringBuilder().append("CREATE DIRECTIONAL EDGE ").append(ds.getName()).append("FROM ").append(";").toString();
                //System.out.println(edge);
            } else {
                // initializeNode(ds);
            }
            commit();
        }



        public void startTransaction() {

        }

        public void commit() {
           /* try {
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/

        }

        public void rollback() {
           /* try {
               // con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        }

        public void close() throws Exception {
           // con.close();
       /* if (g != null) {
            g.close();
        }

        if (graph != null) {
            graph.close();
        }*/
        }




}
