package app;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.*;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import jenkins.tasks.SimpleBuildStep;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author saikat
 * Created on 30/3/2019
 */
public class JenkinsDetailsPublisher extends Notifier implements SimpleBuildStep {

    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    @DataBoundConstructor
    public JenkinsDetailsPublisher() {
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * hudson.tasks.BuildStepCompatibilityLayer#prebuild(hudson.model.AbstractBuild
     * , hudson.model.BuildListener)
     */
    @Override
    public boolean prebuild(AbstractBuild<?, ?> build, BuildListener listener) {
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see hudson.tasks.Publisher#needsToRunAfterFinalized()
     */
    @Override
    public boolean needsToRunAfterFinalized() {
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see hudson.tasks.BuildStep#getRequiredMonitorService()
     */
    @Override
    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    /*
     * (non-Javadoc)
     *
     * @see hudson.tasks.Notifier#getDescriptor()
     */
    @Override
    public BuildStepDescriptor<Publisher> getDescriptor() {
        return DESCRIPTOR;
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * hudson.tasks.BuildStepCompatibilityLayer#perform(hudson.model.AbstractBuild
     * , hudson.Launcher, hudson.model.BuildListener)
     */
    @Override
    public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener){
        try {

            System.out.println("=====================================================BuildStep performed "+build.getParent().getBuildDir().getAbsolutePath()+ "/" + build.number + "/build.xml" +"=======================================");
           new Thread(new XMLScraper(new File(build.getParent().getBuildDir().getAbsolutePath() + "/" + build.number + "/build.xml"))).start();
           new Thread(new LogScraper(build.getLogFile())).start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return true;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath filePath, @Nonnull Launcher launcher, @Nonnull TaskListener taskListener) {
        try {
            System.out.println("=====================================================BuildStep performed=======================================");
            System.out.println("=====================================================BuildStep performed======================================="+run.getDuration());
            System.out.println("=====================================================BuildStep performed======================================="+run.getResult());
            System.out.println("=====================================================BuildStep performed======================================="+run.getAllActions());
            new Thread(new XMLScraper(new File(run.getParent().getBuildDir().getAbsolutePath() + "/" + run.number + "/build.xml"))).start();
            new Thread(new LogScraper(run.getLogFile())).start();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }


    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        @Nonnull
        @Override
        public String getDisplayName() {
            return "Publish project stats to Xarvis";
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }
    }
}
