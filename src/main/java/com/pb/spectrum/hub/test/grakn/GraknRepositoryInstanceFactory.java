package com.pb.spectrum.hub.test.grakn;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.security.auth.login.Configuration;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import grakn.client.GraknClient;
import grakn.client.GraknClient;
import graql.lang.Graql;
//import static graql.lang.Graql.*;

import graql.lang.query.GraqlGet;
import graql.lang.query.GraqlInsert;
import grakn.client.answer.ConceptMap;

import com.pb.spectrum.hub.test.harness.repository.RepositoryInstance;
import com.pb.spectrum.hub.test.harness.repository.RepositoryInstanceFactory;
import com.pb.spectrum.hub.test.harness.repository.RepositoryInstanceFactoryRegistry;
import com.pb.spectrum.hub.test.harness.tests.RepositoryTestRegistry;
/*import graql.lang.Graql;
import graql.lang.Graql.*;
import graql.lang.query.GraqlInsert;
import org.apache.commons.configuration.PropertiesConfiguration;*/
import org.springframework.stereotype.Component;

import static graql.lang.Graql.var;

@Component
public class GraknRepositoryInstanceFactory implements RepositoryInstanceFactory{

        private RepositoryInstanceFactoryRegistry registry;
        private Configuration configuration;


    GraknRepositoryInstanceFactory(RepositoryInstanceFactoryRegistry registry)
        {
            System.out.println("adding TigerGraph to registry");
            this.registry = registry;
        }

        @PostConstruct
        public void initialize()
        {
            registry.register("grakn", this);
        }

        @Override
        public void connect(String filePath) throws Exception
        {
            //configuration = new PropertiesConfiguration(filePath);
        }

        @Override
        public void connect(String url, String username, String password) throws Exception
        {
            Properties properties = new Properties();

            /**
             * Need to specify username and password once REST++ authentication is enabled.
             */
            properties.put("username", username);
            properties.put("password", password);
            properties.put("graph", "myTestHarness");
            properties.put("query_timeout", "90000");
            //properties.put("debug", "2");
            StringBuilder sb = new StringBuilder();
            sb.append("jdbc:tg:http://").append(url).append(":").append(14240);
            GraknClient client = new GraknClient("192.168.1.232:48555");
            GraknClient.Session session = client.session("social_network");
            GraknClient.Transaction writeTransaction = session.transaction().write();
            GraqlInsert insertQuery = Graql.insert(var("x").isa("person").has("email", "x@email.com"));
            List<ConceptMap> insertedId = writeTransaction.execute(insertQuery);
            System.out.println("Inserted a person with ID: " + insertedId.get(0).get("x").id());
            // to persist changes, a write transaction must always be committed (closed)
            writeTransaction.commit();
            GraknClient.Transaction readTransaction = session.transaction().read();
            GraqlGet getQuery = Graql.match(var("p").isa("person")).get().limit(10);
            Stream<ConceptMap> answers = readTransaction.stream(getQuery);
            answers.forEach(answer -> System.out.println(answer.get("p").id()));

            // transactions, sessions and clients must always be closed
            readTransaction.close();
            session.close();
            client.close();

        }

        @Override
        public RepositoryInstance createInstance(RepositoryTestRegistry.TestType testType)
        {
            switch (testType)
            {
                /*case addNodes:
                    return new AddNodesRepository(graph);

                case addEdges:
                    return new AddEdgesRepository(graph);

                case addNodesBatch:
                    return new AddNodesBatchRepository(graph);

                case addEdgesBatch:
                    return new AddEdgesBatchRepository(graph);

                case searchNodes:
                    return new SearchNodesRepository(graph);

                case searchEdges:
                    return new SearchEdgesRepository(graph);

                case addNodeProperty:
                    return new AddNodePropertyRepository(graph);

                case addEdgeProperty:
                    return new AddEdgePropertyRepository(graph);

                case deleteData:
                    return new DeleteDataRepository(graph);

                case deleteEdges:
                    return new DeleteEdgesRepository(graph);

                case deleteNodeProperty:
                    return new DeleteNodePropertyRepository(graph);

                case deleteEdgeProperty:
                    return new DeleteEdgePropertyRepository(graph);

                case aggregations:
                    return new AggregationsTestRepository(graph);*/

                default:
                    throw new IllegalArgumentException("No repo for test type " + testType);

            }
            //return null;
        }


        @Override
        @PreDestroy
        public void close() throws Exception
        {
        }




}
