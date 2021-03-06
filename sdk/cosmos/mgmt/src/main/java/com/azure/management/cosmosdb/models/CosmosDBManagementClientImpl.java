// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.cosmosdb.models;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.management.AzureEnvironment;
import com.azure.management.AzureServiceClient;

/** Initializes a new instance of the CosmosDBManagementClientImpl type. */
public final class CosmosDBManagementClientImpl extends AzureServiceClient {
    /** Azure subscription ID. */
    private String subscriptionId;

    /**
     * Gets Azure subscription ID.
     *
     * @return the subscriptionId value.
     */
    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Sets Azure subscription ID.
     *
     * @param subscriptionId the subscriptionId value.
     * @return the service client itself.
     */
    public CosmosDBManagementClientImpl setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /** server parameter. */
    private String host;

    /**
     * Gets server parameter.
     *
     * @return the host value.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Sets server parameter.
     *
     * @param host the host value.
     * @return the service client itself.
     */
    public CosmosDBManagementClientImpl setHost(String host) {
        this.host = host;
        return this;
    }

    /** The HTTP pipeline to send requests through. */
    private final HttpPipeline httpPipeline;

    /**
     * Gets The HTTP pipeline to send requests through.
     *
     * @return the httpPipeline value.
     */
    public HttpPipeline getHttpPipeline() {
        return this.httpPipeline;
    }

    /** The DatabaseAccountsInner object to access its operations. */
    private final DatabaseAccountsInner databaseAccounts;

    /**
     * Gets the DatabaseAccountsInner object to access its operations.
     *
     * @return the DatabaseAccountsInner object.
     */
    public DatabaseAccountsInner databaseAccounts() {
        return this.databaseAccounts;
    }

    /** The OperationsInner object to access its operations. */
    private final OperationsInner operations;

    /**
     * Gets the OperationsInner object to access its operations.
     *
     * @return the OperationsInner object.
     */
    public OperationsInner operations() {
        return this.operations;
    }

    /** The DatabasesInner object to access its operations. */
    private final DatabasesInner databases;

    /**
     * Gets the DatabasesInner object to access its operations.
     *
     * @return the DatabasesInner object.
     */
    public DatabasesInner databases() {
        return this.databases;
    }

    /** The CollectionsInner object to access its operations. */
    private final CollectionsInner collections;

    /**
     * Gets the CollectionsInner object to access its operations.
     *
     * @return the CollectionsInner object.
     */
    public CollectionsInner collections() {
        return this.collections;
    }

    /** The CollectionRegionsInner object to access its operations. */
    private final CollectionRegionsInner collectionRegions;

    /**
     * Gets the CollectionRegionsInner object to access its operations.
     *
     * @return the CollectionRegionsInner object.
     */
    public CollectionRegionsInner collectionRegions() {
        return this.collectionRegions;
    }

    /** The DatabaseAccountRegionsInner object to access its operations. */
    private final DatabaseAccountRegionsInner databaseAccountRegions;

    /**
     * Gets the DatabaseAccountRegionsInner object to access its operations.
     *
     * @return the DatabaseAccountRegionsInner object.
     */
    public DatabaseAccountRegionsInner databaseAccountRegions() {
        return this.databaseAccountRegions;
    }

    /** The PercentileSourceTargetsInner object to access its operations. */
    private final PercentileSourceTargetsInner percentileSourceTargets;

    /**
     * Gets the PercentileSourceTargetsInner object to access its operations.
     *
     * @return the PercentileSourceTargetsInner object.
     */
    public PercentileSourceTargetsInner percentileSourceTargets() {
        return this.percentileSourceTargets;
    }

    /** The PercentileTargetsInner object to access its operations. */
    private final PercentileTargetsInner percentileTargets;

    /**
     * Gets the PercentileTargetsInner object to access its operations.
     *
     * @return the PercentileTargetsInner object.
     */
    public PercentileTargetsInner percentileTargets() {
        return this.percentileTargets;
    }

    /** The PercentilesInner object to access its operations. */
    private final PercentilesInner percentiles;

    /**
     * Gets the PercentilesInner object to access its operations.
     *
     * @return the PercentilesInner object.
     */
    public PercentilesInner percentiles() {
        return this.percentiles;
    }

    /** The CollectionPartitionRegionsInner object to access its operations. */
    private final CollectionPartitionRegionsInner collectionPartitionRegions;

    /**
     * Gets the CollectionPartitionRegionsInner object to access its operations.
     *
     * @return the CollectionPartitionRegionsInner object.
     */
    public CollectionPartitionRegionsInner collectionPartitionRegions() {
        return this.collectionPartitionRegions;
    }

    /** The CollectionPartitionsInner object to access its operations. */
    private final CollectionPartitionsInner collectionPartitions;

    /**
     * Gets the CollectionPartitionsInner object to access its operations.
     *
     * @return the CollectionPartitionsInner object.
     */
    public CollectionPartitionsInner collectionPartitions() {
        return this.collectionPartitions;
    }

    /** The PartitionKeyRangeIdsInner object to access its operations. */
    private final PartitionKeyRangeIdsInner partitionKeyRangeIds;

    /**
     * Gets the PartitionKeyRangeIdsInner object to access its operations.
     *
     * @return the PartitionKeyRangeIdsInner object.
     */
    public PartitionKeyRangeIdsInner partitionKeyRangeIds() {
        return this.partitionKeyRangeIds;
    }

    /** The PartitionKeyRangeIdRegionsInner object to access its operations. */
    private final PartitionKeyRangeIdRegionsInner partitionKeyRangeIdRegions;

    /**
     * Gets the PartitionKeyRangeIdRegionsInner object to access its operations.
     *
     * @return the PartitionKeyRangeIdRegionsInner object.
     */
    public PartitionKeyRangeIdRegionsInner partitionKeyRangeIdRegions() {
        return this.partitionKeyRangeIdRegions;
    }

    /** The SqlResourcesInner object to access its operations. */
    private final SqlResourcesInner sqlResources;

    /**
     * Gets the SqlResourcesInner object to access its operations.
     *
     * @return the SqlResourcesInner object.
     */
    public SqlResourcesInner sqlResources() {
        return this.sqlResources;
    }

    /** The MongoDBResourcesInner object to access its operations. */
    private final MongoDBResourcesInner mongoDBResources;

    /**
     * Gets the MongoDBResourcesInner object to access its operations.
     *
     * @return the MongoDBResourcesInner object.
     */
    public MongoDBResourcesInner mongoDBResources() {
        return this.mongoDBResources;
    }

    /** The TableResourcesInner object to access its operations. */
    private final TableResourcesInner tableResources;

    /**
     * Gets the TableResourcesInner object to access its operations.
     *
     * @return the TableResourcesInner object.
     */
    public TableResourcesInner tableResources() {
        return this.tableResources;
    }

    /** The CassandraResourcesInner object to access its operations. */
    private final CassandraResourcesInner cassandraResources;

    /**
     * Gets the CassandraResourcesInner object to access its operations.
     *
     * @return the CassandraResourcesInner object.
     */
    public CassandraResourcesInner cassandraResources() {
        return this.cassandraResources;
    }

    /** The GremlinResourcesInner object to access its operations. */
    private final GremlinResourcesInner gremlinResources;

    /**
     * Gets the GremlinResourcesInner object to access its operations.
     *
     * @return the GremlinResourcesInner object.
     */
    public GremlinResourcesInner gremlinResources() {
        return this.gremlinResources;
    }

    /** The NotebookWorkspacesInner object to access its operations. */
    private final NotebookWorkspacesInner notebookWorkspaces;

    /**
     * Gets the NotebookWorkspacesInner object to access its operations.
     *
     * @return the NotebookWorkspacesInner object.
     */
    public NotebookWorkspacesInner notebookWorkspaces() {
        return this.notebookWorkspaces;
    }

    /** The PrivateLinkResourcesInner object to access its operations. */
    private final PrivateLinkResourcesInner privateLinkResources;

    /**
     * Gets the PrivateLinkResourcesInner object to access its operations.
     *
     * @return the PrivateLinkResourcesInner object.
     */
    public PrivateLinkResourcesInner privateLinkResources() {
        return this.privateLinkResources;
    }

    /** The PrivateEndpointConnectionsInner object to access its operations. */
    private final PrivateEndpointConnectionsInner privateEndpointConnections;

    /**
     * Gets the PrivateEndpointConnectionsInner object to access its operations.
     *
     * @return the PrivateEndpointConnectionsInner object.
     */
    public PrivateEndpointConnectionsInner privateEndpointConnections() {
        return this.privateEndpointConnections;
    }

    /** Initializes an instance of CosmosDBManagementClient client. */
    public CosmosDBManagementClientImpl() {
        this(
            new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy()).build(),
            AzureEnvironment.AZURE);
    }

    /**
     * Initializes an instance of CosmosDBManagementClient client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     */
    public CosmosDBManagementClientImpl(HttpPipeline httpPipeline) {
        this(httpPipeline, AzureEnvironment.AZURE);
    }

    /**
     * Initializes an instance of CosmosDBManagementClient client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param environment The Azure environment.
     */
    public CosmosDBManagementClientImpl(HttpPipeline httpPipeline, AzureEnvironment environment) {
        super(httpPipeline, environment);
        this.httpPipeline = httpPipeline;
        this.databaseAccounts = new DatabaseAccountsInner(this);
        this.operations = new OperationsInner(this);
        this.databases = new DatabasesInner(this);
        this.collections = new CollectionsInner(this);
        this.collectionRegions = new CollectionRegionsInner(this);
        this.databaseAccountRegions = new DatabaseAccountRegionsInner(this);
        this.percentileSourceTargets = new PercentileSourceTargetsInner(this);
        this.percentileTargets = new PercentileTargetsInner(this);
        this.percentiles = new PercentilesInner(this);
        this.collectionPartitionRegions = new CollectionPartitionRegionsInner(this);
        this.collectionPartitions = new CollectionPartitionsInner(this);
        this.partitionKeyRangeIds = new PartitionKeyRangeIdsInner(this);
        this.partitionKeyRangeIdRegions = new PartitionKeyRangeIdRegionsInner(this);
        this.sqlResources = new SqlResourcesInner(this);
        this.mongoDBResources = new MongoDBResourcesInner(this);
        this.tableResources = new TableResourcesInner(this);
        this.cassandraResources = new CassandraResourcesInner(this);
        this.gremlinResources = new GremlinResourcesInner(this);
        this.notebookWorkspaces = new NotebookWorkspacesInner(this);
        this.privateLinkResources = new PrivateLinkResourcesInner(this);
        this.privateEndpointConnections = new PrivateEndpointConnectionsInner(this);
    }
}
