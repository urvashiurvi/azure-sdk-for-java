// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.containerregistry;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/** The RunFilter model. */
@Fluent
public final class RunFilter {
    /*
     * The unique identifier for the run.
     */
    @JsonProperty(value = "runId")
    private String runId;

    /*
     * The type of run.
     */
    @JsonProperty(value = "runType")
    private RunType runType;

    /*
     * The current status of the run.
     */
    @JsonProperty(value = "status")
    private RunStatus status;

    /*
     * The create time for a run.
     */
    @JsonProperty(value = "createTime")
    private OffsetDateTime createTime;

    /*
     * The time the run finished.
     */
    @JsonProperty(value = "finishTime")
    private OffsetDateTime finishTime;

    /*
     * The list of comma-separated image manifests that were generated from the
     * run. This is applicable if the run is of
     * build type.
     */
    @JsonProperty(value = "outputImageManifests")
    private String outputImageManifests;

    /*
     * The value that indicates whether archiving is enabled or not.
     */
    @JsonProperty(value = "isArchiveEnabled")
    private Boolean isArchiveEnabled;

    /*
     * The name of the task that the run corresponds to.
     */
    @JsonProperty(value = "taskName")
    private String taskName;

    /**
     * Get the runId property: The unique identifier for the run.
     *
     * @return the runId value.
     */
    public String runId() {
        return this.runId;
    }

    /**
     * Set the runId property: The unique identifier for the run.
     *
     * @param runId the runId value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withRunId(String runId) {
        this.runId = runId;
        return this;
    }

    /**
     * Get the runType property: The type of run.
     *
     * @return the runType value.
     */
    public RunType runType() {
        return this.runType;
    }

    /**
     * Set the runType property: The type of run.
     *
     * @param runType the runType value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withRunType(RunType runType) {
        this.runType = runType;
        return this;
    }

    /**
     * Get the status property: The current status of the run.
     *
     * @return the status value.
     */
    public RunStatus status() {
        return this.status;
    }

    /**
     * Set the status property: The current status of the run.
     *
     * @param status the status value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withStatus(RunStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Get the createTime property: The create time for a run.
     *
     * @return the createTime value.
     */
    public OffsetDateTime createTime() {
        return this.createTime;
    }

    /**
     * Set the createTime property: The create time for a run.
     *
     * @param createTime the createTime value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * Get the finishTime property: The time the run finished.
     *
     * @return the finishTime value.
     */
    public OffsetDateTime finishTime() {
        return this.finishTime;
    }

    /**
     * Set the finishTime property: The time the run finished.
     *
     * @param finishTime the finishTime value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withFinishTime(OffsetDateTime finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    /**
     * Get the outputImageManifests property: The list of comma-separated image manifests that were generated from the
     * run. This is applicable if the run is of build type.
     *
     * @return the outputImageManifests value.
     */
    public String outputImageManifests() {
        return this.outputImageManifests;
    }

    /**
     * Set the outputImageManifests property: The list of comma-separated image manifests that were generated from the
     * run. This is applicable if the run is of build type.
     *
     * @param outputImageManifests the outputImageManifests value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withOutputImageManifests(String outputImageManifests) {
        this.outputImageManifests = outputImageManifests;
        return this;
    }

    /**
     * Get the isArchiveEnabled property: The value that indicates whether archiving is enabled or not.
     *
     * @return the isArchiveEnabled value.
     */
    public Boolean isArchiveEnabled() {
        return this.isArchiveEnabled;
    }

    /**
     * Set the isArchiveEnabled property: The value that indicates whether archiving is enabled or not.
     *
     * @param isArchiveEnabled the isArchiveEnabled value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withIsArchiveEnabled(Boolean isArchiveEnabled) {
        this.isArchiveEnabled = isArchiveEnabled;
        return this;
    }

    /**
     * Get the taskName property: The name of the task that the run corresponds to.
     *
     * @return the taskName value.
     */
    public String taskName() {
        return this.taskName;
    }

    /**
     * Set the taskName property: The name of the task that the run corresponds to.
     *
     * @param taskName the taskName value to set.
     * @return the RunFilter object itself.
     */
    public RunFilter withTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }
}
