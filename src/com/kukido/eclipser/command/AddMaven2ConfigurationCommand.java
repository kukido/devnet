package com.kukido.eclipser.command;

import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.impl.RunManagerImpl;
import com.intellij.openapi.project.Project;
import com.kukido.eclipser.EclipserException;
import com.kukido.eclipser.configuration.Maven2Configuration;
import org.jetbrains.idea.maven.execution.MavenRunConfigurationType;
import org.jetbrains.idea.maven.execution.MavenRunnerParameters;

public class AddMaven2ConfigurationCommand implements Command {

    private Maven2Configuration maven2Configuration;

    public AddMaven2ConfigurationCommand(Maven2Configuration conf) {
        this.maven2Configuration = conf;
    }

    @Override
    public void execute(Project project) throws EclipserException {
        // todo: check configuration with the same name
        MavenRunnerParameters parameters = new MavenRunnerParameters();
        parameters.setGoals(maven2Configuration.getGoals());
        parameters.setProfilesMap(maven2Configuration.getProfilesMap());
        parameters.setWorkingDirPath(maven2Configuration.getWorkingDirectory());
        parameters.setResolveToWorkspace(maven2Configuration.isResolveToWorkspace());

        RunnerAndConfigurationSettings settings = MavenRunConfigurationType.createRunnerAndConfigurationSettings(null, null, parameters, project);
        settings.setName(maven2Configuration.getConfigurationName());

        RunManagerImpl runManager = (RunManagerImpl) RunManager.getInstance(project);
        runManager.addConfiguration(settings, false);
        runManager.setSelectedConfiguration(settings);
    }
}
