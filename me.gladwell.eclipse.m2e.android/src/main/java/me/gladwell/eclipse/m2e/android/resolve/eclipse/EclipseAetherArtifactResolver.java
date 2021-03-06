/*******************************************************************************
 * Copyright (c) 2014 Ricardo Gladwell
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package me.gladwell.eclipse.m2e.android.resolve.eclipse;

import java.util.List;

import me.gladwell.eclipse.m2e.android.configuration.ProjectConfigurationException;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

import com.google.inject.Inject;

class EclipseAetherArtifactResolver implements ArtifactResolver {

    private final RepositorySystem repository;
    private final RepositorySystemSession session;

    @Inject
    public EclipseAetherArtifactResolver(RepositorySystem repository, RepositorySystemSession session) {
        super();
        this.repository = repository;
        this.session = session;
    }

    public Artifact resolveArtifact(List<RemoteRepository> repositories, Artifact artifact) {
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(artifact);

        for (RemoteRepository remoteRepository : repositories) {
            artifactRequest.addRepository(remoteRepository);
        }
        
        ArtifactResult artifactResult;
        try {
            artifactResult = repository.resolveArtifact(session, artifactRequest);
            return artifactResult.getArtifact();
        } catch (ArtifactResolutionException e) {
            throw new ProjectConfigurationException(e);
        }
    }

}
