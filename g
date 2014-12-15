12:22:25.915 [INFO] [org.gradle.BuildLogger] Starting Build
12:22:25.916 [DEBUG] [org.gradle.BuildLogger] Gradle user home: /home/flo/.gradle
12:22:25.917 [DEBUG] [org.gradle.BuildLogger] Current dir: /home/flo/workspace/Tools
12:22:25.917 [DEBUG] [org.gradle.BuildLogger] Settings file: null
12:22:25.918 [DEBUG] [org.gradle.BuildLogger] Build file: null
12:22:25.930 [DEBUG] [org.gradle.initialization.buildsrc.BuildSourceBuilder] Starting to build the build sources.
12:22:25.931 [DEBUG] [org.gradle.initialization.buildsrc.BuildSourceBuilder] Gradle source dir does not exist. We leave.
12:22:25.932 [DEBUG] [org.gradle.initialization.DefaultGradlePropertiesLoader] Found env project properties: []
12:22:25.932 [DEBUG] [org.gradle.initialization.DefaultGradlePropertiesLoader] Found system project properties: []
12:22:26.022 [DEBUG] [org.gradle.api.internal.artifacts.mvnsettings.DefaultLocalMavenRepositoryLocator] No local repository in Settings file defined. Using default path: /home/flo/.m2/repository
12:22:26.289 [DEBUG] [org.gradle.initialization.ScriptEvaluatingSettingsProcessor] Timing: Processing settings took: 0.356 secs
12:22:26.290 [INFO] [org.gradle.BuildLogger] Settings evaluated using empty settings script.
12:22:26.413 [DEBUG] [org.gradle.initialization.ProjectPropertySettingBuildLoader] Looking for project properties from: /home/flo/workspace/Tools/gradle.properties
12:22:26.414 [DEBUG] [org.gradle.initialization.ProjectPropertySettingBuildLoader] project property file does not exists. We continue!
12:22:26.415 [INFO] [org.gradle.BuildLogger] Projects loaded. Root project using build file '/home/flo/workspace/Tools/build.gradle'.
12:22:26.416 [INFO] [org.gradle.BuildLogger] Included projects: [root project 'Tools']
12:22:26.854 [INFO] [org.gradle.configuration.project.BuildScriptProcessor] Evaluating root project 'Tools' using build file '/home/flo/workspace/Tools/build.gradle'.
12:22:26.873 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Waiting to acquire shared lock on buildscript class cache for build file '/home/flo/workspace/Tools/build.gradle' (/home/flo/.gradle/caches/1.11/scripts/build_50qv936hqeqf60ceghae3hc8o4/ProjectScript/buildscript).
12:22:26.874 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Lock acquired.
12:22:26.887 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Waiting to acquire shared lock on no_buildscript class cache for build file '/home/flo/workspace/Tools/build.gradle' (/home/flo/.gradle/caches/1.11/scripts/build_50qv936hqeqf60ceghae3hc8o4/ProjectScript/no_buildscript).
12:22:26.888 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Lock acquired.
12:22:28.549 [DEBUG] [org.gradle.configuration.project.BuildScriptProcessor] Timing: Running the build script took 1.695 secs
12:22:28.563 [INFO] [org.gradle.BuildLogger] All projects evaluated.
12:22:28.648 [DEBUG] [org.gradle.execution.taskgraph.DefaultTaskGraphExecuter] Timing: Creating the DAG took 0.016 secs
12:22:28.649 [INFO] [org.gradle.execution.TaskNameResolvingBuildConfigurationAction] Selected primary task 'setup'
12:22:28.651 [INFO] [org.gradle.BuildLogger] Tasks to be executed: [task ':cleanEclipseClasspath', task ':cleanEclipseJdt', task ':cleanEclipseProject', task ':cleanEclipseWtp', task ':cleanEclipse', task ':eclipseClasspath', task ':eclipseJdt', task ':eclipseProject', task ':eclipseWtp', task ':eclipse', task ':setup']
12:22:28.655 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseClasspath (Thread[main,5,main]) started.
12:22:28.656 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :cleanEclipseClasspath
12:22:28.658 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':cleanEclipseClasspath'
12:22:28.659 [DEBUG] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Determining if task ':cleanEclipseClasspath' is up-to-date
12:22:28.661 [INFO] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Executing task ':cleanEclipseClasspath' (up-to-date check took 0.001 secs) due to:
  Task has not declared any outputs.
12:22:28.662 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter] Executing actions for task ':cleanEclipseClasspath'.
12:22:28.666 [DEBUG] [org.gradle.api.internal.file.copy.DeleteActionImpl] Deleting /home/flo/workspace/Tools/.classpath
12:22:28.667 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':cleanEclipseClasspath'
12:22:28.668 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseClasspath (Thread[main,5,main]) completed. Took 0.011 secs.
12:22:28.668 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseJdt (Thread[main,5,main]) started.
12:22:28.668 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :cleanEclipseJdt
12:22:28.669 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':cleanEclipseJdt'
12:22:28.669 [DEBUG] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Determining if task ':cleanEclipseJdt' is up-to-date
12:22:28.670 [INFO] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Executing task ':cleanEclipseJdt' (up-to-date check took 0.0 secs) due to:
  Task has not declared any outputs.
12:22:28.670 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter] Executing actions for task ':cleanEclipseJdt'.
12:22:28.671 [DEBUG] [org.gradle.api.internal.file.copy.DeleteActionImpl] Deleting /home/flo/workspace/Tools/.settings/org.eclipse.jdt.core.prefs
12:22:28.672 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':cleanEclipseJdt'
12:22:28.673 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseJdt (Thread[main,5,main]) completed. Took 0.005 secs.
12:22:28.673 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseProject (Thread[main,5,main]) started.
12:22:28.674 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :cleanEclipseProject
12:22:28.675 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':cleanEclipseProject'
12:22:28.675 [DEBUG] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Determining if task ':cleanEclipseProject' is up-to-date
12:22:28.676 [INFO] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Executing task ':cleanEclipseProject' (up-to-date check took 0.0 secs) due to:
  Task has not declared any outputs.
12:22:28.677 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter] Executing actions for task ':cleanEclipseProject'.
12:22:28.677 [DEBUG] [org.gradle.api.internal.file.copy.DeleteActionImpl] Deleting /home/flo/workspace/Tools/.project
12:22:28.678 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':cleanEclipseProject'
12:22:28.679 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseProject (Thread[main,5,main]) completed. Took 0.005 secs.
12:22:28.680 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseWtp (Thread[main,5,main]) started.
12:22:28.680 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :cleanEclipseWtp
12:22:28.681 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':cleanEclipseWtp'
12:22:28.682 [INFO] [org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter] Skipping task ':cleanEclipseWtp' as it has no actions.
12:22:28.683 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':cleanEclipseWtp'
12:22:28.684 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :cleanEclipseWtp UP-TO-DATE
12:22:28.685 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipseWtp (Thread[main,5,main]) completed. Took 0.005 secs.
12:22:28.686 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipse (Thread[main,5,main]) started.
12:22:28.686 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :cleanEclipse
12:22:28.687 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':cleanEclipse'
12:22:28.688 [INFO] [org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter] Skipping task ':cleanEclipse' as it has no actions.
12:22:28.688 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':cleanEclipse'
12:22:28.689 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :cleanEclipse (Thread[main,5,main]) completed. Took 0.003 secs.
12:22:28.690 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseClasspath (Thread[main,5,main]) started.
12:22:28.690 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :eclipseClasspath
12:22:28.691 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':eclipseClasspath'
12:22:28.692 [DEBUG] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Determining if task ':eclipseClasspath' is up-to-date
12:22:28.694 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Waiting to acquire exclusive lock on task history cache (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts).
12:22:28.697 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Lock acquired.
12:22:28.701 [DEBUG] [org.gradle.cache.internal.locklistener.DefaultFileLockContentionHandler] Starting file lock listener thread.
12:22:28.715 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache taskArtifacts.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/taskArtifacts.bin)
12:22:28.742 [INFO] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Executing task ':eclipseClasspath' (up-to-date check took 0.049 secs) due to:
  Task.upToDateWhen is false.
12:22:28.743 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter] Executing actions for task ':eclipseClasspath'.
12:22:29.131 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Waiting to acquire exclusive lock on artifact cache (/home/flo/.gradle/caches/modules-2).
12:22:29.132 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Lock acquired.
12:22:29.133 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DefaultDependencyResolver] Resolving configuration ':testRuntime'
12:22:29.161 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'ivy.default.settings.dir' to 'jar:file:/home/flo/java/gradle-1.11/lib/ivy-2.2.0.jar!/org/apache/ivy/core/settings'
12:22:29.164 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'ivy.basedir' to '/home/flo/workspace/Tools/.'
12:22:29.165 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'ivy.default.conf.dir' to 'jar:file:/home/flo/java/gradle-1.11/lib/ivy-2.2.0.jar!/org/apache/ivy/core/settings'
12:22:29.205 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'jna.platform.library.path' to '/lib64'
12:22:29.206 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.runtime.name' to 'Java(TM) SE Runtime Environment'
12:22:29.206 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vm.version' to '25.25-b02'
12:22:29.207 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.boot.library.path' to '/usr/lib/jvm/java-8-oracle/jre/lib/amd64'
12:22:29.208 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vm.vendor' to 'Oracle Corporation'
12:22:29.208 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vendor.url' to 'http://java.oracle.com/'
12:22:29.209 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'path.separator' to ':'
12:22:29.209 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vm.name' to 'Java HotSpot(TM) 64-Bit Server VM'
12:22:29.210 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'file.encoding.pkg' to 'sun.io'
12:22:29.210 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.os.patch.level' to 'unknown'
12:22:29.211 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'user.country' to 'US'
12:22:29.212 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.java.launcher' to 'SUN_STANDARD'
12:22:29.212 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vm.specification.name' to 'Java Virtual Machine Specification'
12:22:29.213 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'user.dir' to '/home/flo/workspace/Tools'
12:22:29.213 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.runtime.version' to '1.8.0_25-b17'
12:22:29.214 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.awt.graphicsenv' to 'sun.awt.X11GraphicsEnvironment'
12:22:29.214 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'os.arch' to 'amd64'
12:22:29.215 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.endorsed.dirs' to '/usr/lib/jvm/java-8-oracle/jre/lib/endorsed'
12:22:29.215 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.io.tmpdir' to '/tmp'
12:22:29.216 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'line.separator' to '
'
12:22:29.216 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'org.gradle.appname' to 'gradle'
12:22:29.217 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vm.specification.vendor' to 'Oracle Corporation'
12:22:29.217 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'os.name' to 'Linux'
12:22:29.218 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'jna.boot.library.path' to '/home/flo/.gradle/native/jna/linux-amd64'
12:22:29.218 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.jnu.encoding' to 'UTF-8'
12:22:29.219 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.library.path' to '/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib'
12:22:29.219 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.class.version' to '52.0'
12:22:29.220 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.specification.name' to 'Java Platform API Specification'
12:22:29.220 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.management.compiler' to 'HotSpot 64-Bit Tiered Compilers'
12:22:29.221 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'os.version' to '3.13.0-39-generic'
12:22:29.221 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'user.home' to '/home/flo'
12:22:29.222 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'user.timezone' to 'Europe/Berlin'
12:22:29.222 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.awt.printerjob' to 'sun.print.PSPrinterJob'
12:22:29.223 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'file.encoding' to 'UTF-8'
12:22:29.224 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.specification.version' to '1.8'
12:22:29.224 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'user.name' to 'flo'
12:22:29.225 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.class.path' to '/home/flo/java/gradle-1.11/lib/gradle-launcher-1.11.jar'
12:22:29.225 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vm.specification.version' to '1.8'
12:22:29.226 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.arch.data.model' to '64'
12:22:29.226 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.java.command' to 'org.gradle.launcher.GradleMain -d setup'
12:22:29.227 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.home' to '/usr/lib/jvm/java-8-oracle/jre'
12:22:29.227 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'user.language' to 'en'
12:22:29.228 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.specification.vendor' to 'Oracle Corporation'
12:22:29.228 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'awt.toolkit' to 'sun.awt.X11.XToolkit'
12:22:29.229 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vm.info' to 'mixed mode'
12:22:29.229 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.version' to '1.8.0_25'
12:22:29.230 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.ext.dirs' to '/usr/lib/jvm/java-8-oracle/jre/lib/ext:/usr/java/packages/lib/ext'
12:22:29.230 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.boot.class.path' to '/usr/lib/jvm/java-8-oracle/jre/lib/resources.jar:/usr/lib/jvm/java-8-oracle/jre/lib/rt.jar:/usr/lib/jvm/java-8-oracle/jre/lib/sunrsasign.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jsse.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jce.jar:/usr/lib/jvm/java-8-oracle/jre/lib/charsets.jar:/usr/lib/jvm/java-8-oracle/jre/lib/jfr.jar:/usr/lib/jvm/java-8-oracle/jre/classes'
12:22:29.231 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vendor' to 'Oracle Corporation'
12:22:29.231 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'file.separator' to '/'
12:22:29.232 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'java.vendor.url.bug' to 'http://bugreport.sun.com/bugreport/'
12:22:29.232 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.io.unicode.encoding' to 'UnicodeLittle'
12:22:29.233 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.cpu.endian' to 'little'
12:22:29.233 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.IvyLoggingAdaper] setting 'sun.cpu.isalist' to ''
12:22:29.351 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.memcache.InMemoryDependencyMetadataCache] Creating new in-memory cache for repo 'MavenRepo' [e9d03b7c6586155fbee8fb2de8b5b149].
12:22:29.420 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration :Tools:1.0.0(testRuntime).
12:22:29.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> commons-codec:commons-codec:1.4(dependency: commons-codec#commons-codec;1.4 {compile=[default]})
12:22:29.430 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version commons-codec:commons-codec:1.4
12:22:29.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'commons-codec:commons-codec:1.4' using repositories [MavenRepo]
12:22:29.435 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache dynamic-revisions.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/dynamic-revisions.bin)
12:22:29.440 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache module-metadata.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/module-metadata.bin)
12:22:29.473 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.481 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'commons-codec#commons-codec;1.4' in 'MavenRepo'
12:22:29.484 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'commons-codec:commons-codec:1.4' from repository 'MavenRepo'
12:22:29.485 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> commons-lang:commons-lang:2.6(dependency: commons-lang#commons-lang;2.6 {compile=[default]})
12:22:29.486 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version commons-lang:commons-lang:2.6
12:22:29.486 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'commons-lang:commons-lang:2.6' using repositories [MavenRepo]
12:22:29.490 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.493 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'commons-lang#commons-lang;2.6' in 'MavenRepo'
12:22:29.494 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'commons-lang:commons-lang:2.6' from repository 'MavenRepo'
12:22:29.494 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.hibernate.common:hibernate-commons-annotations:4.0+(dependency: org.hibernate.common#hibernate-commons-annotations;4.0+ {compile=[default]})
12:22:29.496 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.hibernate.common:hibernate-commons-annotations:4.0+' using repositories [MavenRepo]
12:22:29.500 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.hibernate.common:hibernate-commons-annotations:4.0.5.Final' for 'org.hibernate.common:hibernate-commons-annotations:4.0+'
12:22:29.505 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.509 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.hibernate.common#hibernate-commons-annotations;4.0.5.Final' in 'MavenRepo'
12:22:29.510 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.hibernate.common:hibernate-commons-annotations:4.0.5.Final' from repository 'MavenRepo'
12:22:29.510 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.hibernate.common:hibernate-commons-annotations:4.0.5.Final
12:22:29.511 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0+(dependency: org.hibernate.javax.persistence#hibernate-jpa-2.1-api;1.0+ {compile=[default]})
12:22:29.512 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0+' using repositories [MavenRepo]
12:22:29.513 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final' for 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0+'
12:22:29.516 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.519 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.hibernate.javax.persistence#hibernate-jpa-2.1-api;1.0.0.Final' in 'MavenRepo'
12:22:29.520 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final' from repository 'MavenRepo'
12:22:29.520 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final
12:22:29.521 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> net.sf.ehcache:ehcache-core:2.5.2(dependency: net.sf.ehcache#ehcache-core;2.5.2 {compile=[default]})
12:22:29.521 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version net.sf.ehcache:ehcache-core:2.5.2
12:22:29.521 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'net.sf.ehcache:ehcache-core:2.5.2' using repositories [MavenRepo]
12:22:29.525 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.535 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'net.sf.ehcache#ehcache-core;2.5.2' in 'MavenRepo'
12:22:29.536 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'net.sf.ehcache:ehcache-core:2.5.2' from repository 'MavenRepo'
12:22:29.536 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.apache.httpcomponents:httpclient:4.3+(dependency: org.apache.httpcomponents#httpclient;4.3+ {compile=[default]})
12:22:29.537 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.apache.httpcomponents:httpclient:4.3+' using repositories [MavenRepo]
12:22:29.537 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.apache.httpcomponents:httpclient:4.3.5' for 'org.apache.httpcomponents:httpclient:4.3+'
12:22:29.540 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.543 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.apache.httpcomponents#httpclient;4.3.5' in 'MavenRepo'
12:22:29.544 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.apache.httpcomponents:httpclient:4.3.5' from repository 'MavenRepo'
12:22:29.544 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.apache.httpcomponents:httpclient:4.3.5
12:22:29.544 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> com.fasterxml.jackson.core:jackson-databind:2.4+(dependency: com.fasterxml.jackson.core#jackson-databind;2.4+ {compile=[default]})
12:22:29.545 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.fasterxml.jackson.core:jackson-databind:2.4+' using repositories [MavenRepo]
12:22:29.545 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'com.fasterxml.jackson.core:jackson-databind:2.4.3' for 'com.fasterxml.jackson.core:jackson-databind:2.4+'
12:22:29.549 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.552 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.fasterxml.jackson.core#jackson-databind;2.4.3' in 'MavenRepo'
12:22:29.553 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.fasterxml.jackson.core:jackson-databind:2.4.3' from repository 'MavenRepo'
12:22:29.553 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.fasterxml.jackson.core:jackson-databind:2.4.3
12:22:29.553 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.ostermiller:utils:1.07.00(dependency: org.ostermiller#utils;1.07.00 {compile=[default]})
12:22:29.554 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.ostermiller:utils:1.07.00
12:22:29.554 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.ostermiller:utils:1.07.00' using repositories [MavenRepo]
12:22:29.558 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.559 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.ostermiller#utils;1.07.00' in 'MavenRepo'
12:22:29.560 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.ostermiller:utils:1.07.00' from repository 'MavenRepo'
12:22:29.560 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.slf4j:slf4j-api:1.7+(dependency: org.slf4j#slf4j-api;1.7+ {compile=[default]})
12:22:29.560 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.slf4j:slf4j-api:1.7+' using repositories [MavenRepo]
12:22:29.561 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.slf4j:slf4j-api:1.7.7' for 'org.slf4j:slf4j-api:1.7+'
12:22:29.564 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.566 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.slf4j#slf4j-api;1.7.7' in 'MavenRepo'
12:22:29.567 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.slf4j:slf4j-api:1.7.7' from repository 'MavenRepo'
12:22:29.567 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.slf4j:slf4j-api:1.7.7
12:22:29.567 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> javax.servlet:javax.servlet-api:3.0.1(dependency: javax.servlet#javax.servlet-api;3.0.1 {compile=[default]})
12:22:29.568 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version javax.servlet:javax.servlet-api:3.0.1
12:22:29.568 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'javax.servlet:javax.servlet-api:3.0.1' using repositories [MavenRepo]
12:22:29.571 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.573 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'javax.servlet#javax.servlet-api;3.0.1' in 'MavenRepo'
12:22:29.573 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'javax.servlet:javax.servlet-api:3.0.1' from repository 'MavenRepo'
12:22:29.574 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> com.yahoo.platform.yui:yuicompressor:2.4.7(dependency: com.yahoo.platform.yui#yuicompressor;2.4.7 {compile=[default]})
12:22:29.575 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.yahoo.platform.yui:yuicompressor:2.4.7
12:22:29.575 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.yahoo.platform.yui:yuicompressor:2.4.7' using repositories [MavenRepo]
12:22:29.578 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.581 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.yahoo.platform.yui#yuicompressor;2.4.7' in 'MavenRepo'
12:22:29.581 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.yahoo.platform.yui:yuicompressor:2.4.7' from repository 'MavenRepo'
12:22:29.582 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> com.google.code.findbugs:annotations:2.0.3(dependency: com.google.code.findbugs#annotations;2.0.3 {compile=[default]})
12:22:29.582 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.google.code.findbugs:annotations:2.0.3
12:22:29.582 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.google.code.findbugs:annotations:2.0.3' using repositories [MavenRepo]
12:22:29.585 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.587 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.google.code.findbugs#annotations;2.0.3' in 'MavenRepo'
12:22:29.587 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.google.code.findbugs:annotations:2.0.3' from repository 'MavenRepo'
12:22:29.587 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.mockito:mockito-all:1.9+(dependency: org.mockito#mockito-all;1.9+ {testCompile=[default]})
12:22:29.587 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.mockito:mockito-all:1.9+' using repositories [MavenRepo]
12:22:29.588 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.mockito:mockito-all:1.9.5' for 'org.mockito:mockito-all:1.9+'
12:22:29.591 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.593 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.mockito#mockito-all;1.9.5' in 'MavenRepo'
12:22:29.593 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.mockito:mockito-all:1.9.5' from repository 'MavenRepo'
12:22:29.593 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.mockito:mockito-all:1.9.5
12:22:29.594 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.assertj:assertj-core:1.6+(dependency: org.assertj#assertj-core;1.6+ {testCompile=[default]})
12:22:29.594 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.assertj:assertj-core:1.6+' using repositories [MavenRepo]
12:22:29.595 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.assertj:assertj-core:1.6.1' for 'org.assertj:assertj-core:1.6+'
12:22:29.598 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.600 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.assertj#assertj-core;1.6.1' in 'MavenRepo'
12:22:29.600 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.assertj:assertj-core:1.6.1' from repository 'MavenRepo'
12:22:29.601 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.assertj:assertj-core:1.6.1
12:22:29.601 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> com.googlecode.catch-exception:catch-exception:1.2+(dependency: com.googlecode.catch-exception#catch-exception;1.2+ {testCompile=[default]})
12:22:29.601 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.googlecode.catch-exception:catch-exception:1.2+' using repositories [MavenRepo]
12:22:29.602 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'com.googlecode.catch-exception:catch-exception:1.2.0' for 'com.googlecode.catch-exception:catch-exception:1.2+'
12:22:29.607 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:29.610 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.googlecode.catch-exception#catch-exception;1.2.0' in 'MavenRepo'
12:22:29.611 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.googlecode.catch-exception:catch-exception:1.2.0' from repository 'MavenRepo'
12:22:29.611 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.googlecode.catch-exception:catch-exception:1.2.0
12:22:29.612 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.apache.jmh:jmh-core:1.1.1(dependency: org.apache.jmh#jmh-core;1.1.1 {testCompile=[default]})
12:22:29.612 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.apache.jmh:jmh-core:1.1.1
12:22:29.612 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.apache.jmh:jmh-core:1.1.1' using repositories [MavenRepo]
12:22:29.613 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Detected non-existence of module 'org.apache.jmh#jmh-core;1.1.1' in resolver cache 'MavenRepo'
12:22:29.616 [DEBUG] [org.gradle.api.internal.artifacts.repositories.resolver.ExternalResourceResolver] Loading http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom
12:22:29.619 [DEBUG] [org.gradle.api.internal.externalresource.transfer.DefaultCacheAwareExternalResourceAccessor] Constructing external resource: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom
12:22:29.620 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache artifact-at-url.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/artifact-at-url.bin)
12:22:29.630 [DEBUG] [org.gradle.api.internal.externalresource.transport.http.HttpResourceAccessor] Constructing external resource: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom
12:22:29.633 [DEBUG] [org.gradle.api.internal.externalresource.transport.http.HttpClientHelper] Performing HTTP GET: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom
12:22:29.856 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection request: [route: {}->http://repo1.maven.org][total kept alive: 0; route allocated: 0 of 5; total allocated: 0 of 10]
12:22:29.869 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection leased: [id: 0][route: {}->http://repo1.maven.org][total kept alive: 0; route allocated: 1 of 5; total allocated: 1 of 10]
12:22:29.878 [DEBUG] [org.apache.http.impl.conn.DefaultClientConnectionOperator] Connecting to repo1.maven.org:80
12:22:30.055 [DEBUG] [org.apache.http.client.protocol.RequestAddCookies] CookieSpec selected: best-match
12:22:30.066 [DEBUG] [org.apache.http.client.protocol.RequestAuthCache] Auth cache not set in the context
12:22:30.066 [DEBUG] [org.apache.http.client.protocol.RequestTargetAuthentication] Target auth state: UNCHALLENGED
12:22:30.067 [DEBUG] [org.apache.http.client.protocol.RequestProxyAuthentication] Proxy auth state: UNCHALLENGED
12:22:30.068 [DEBUG] [org.apache.http.impl.client.SystemDefaultHttpClient] Attempt 1 to execute request
12:22:30.068 [DEBUG] [org.apache.http.impl.conn.DefaultClientConnection] Sending request: GET /maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom HTTP/1.1
12:22:30.071 [DEBUG] [org.apache.http.headers] >> GET /maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom HTTP/1.1
12:22:30.071 [DEBUG] [org.apache.http.headers] >> Accept-Encoding: gzip,deflate
12:22:30.071 [DEBUG] [org.apache.http.headers] >> Host: repo1.maven.org
12:22:30.072 [DEBUG] [org.apache.http.headers] >> Connection: Keep-Alive
12:22:30.072 [DEBUG] [org.apache.http.headers] >> User-Agent: Gradle/1.11 (Linux;3.13.0-39-generic;amd64) (Oracle Corporation;1.8.0_25;25.25-b02)
12:22:30.236 [DEBUG] [org.apache.http.impl.conn.DefaultClientConnection] Receiving response: HTTP/1.1 404 Not Found
12:22:30.236 [DEBUG] [org.apache.http.headers] << HTTP/1.1 404 Not Found
12:22:30.237 [DEBUG] [org.apache.http.headers] << Server: nginx
12:22:30.237 [DEBUG] [org.apache.http.headers] << Content-Type: text/html
12:22:30.238 [DEBUG] [org.apache.http.headers] << Via: 1.1 varnish
12:22:30.238 [DEBUG] [org.apache.http.headers] << Content-Length: 168
12:22:30.239 [DEBUG] [org.apache.http.headers] << Accept-Ranges: bytes
12:22:30.239 [DEBUG] [org.apache.http.headers] << Date: Thu, 06 Nov 2014 11:22:30 GMT
12:22:30.239 [DEBUG] [org.apache.http.headers] << Via: 1.1 varnish
12:22:30.240 [DEBUG] [org.apache.http.headers] << Age: 139
12:22:30.240 [DEBUG] [org.apache.http.headers] << Connection: keep-alive
12:22:30.240 [DEBUG] [org.apache.http.headers] << X-Served-By: cache-iad2132-IAD, cache-fra1229-FRA
12:22:30.241 [DEBUG] [org.apache.http.headers] << X-Cache: MISS, HIT
12:22:30.241 [DEBUG] [org.apache.http.headers] << X-Cache-Hits: 0, 1
12:22:30.241 [DEBUG] [org.apache.http.headers] << X-Timer: S1415272950.073866,VS0,VE0
12:22:30.247 [DEBUG] [org.apache.http.impl.client.SystemDefaultHttpClient] Connection can be kept alive indefinitely
12:22:30.251 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection [id: 0][route: {}->http://repo1.maven.org] can be kept alive indefinitely
12:22:30.251 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection released: [id: 0][route: {}->http://repo1.maven.org][total kept alive: 1; route allocated: 1 of 5; total allocated: 1 of 10]
12:22:30.251 [INFO] [org.gradle.api.internal.externalresource.transport.http.HttpClientHelper] Resource missing. [HTTP GET: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom]
12:22:30.252 [DEBUG] [org.gradle.api.internal.artifacts.repositories.resolver.ExternalResourceResolver] Resource not reachable for org.apache.jmh#jmh-core;1.1.1!jmh-core.pom: res=MissingResource: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.pom
12:22:30.253 [DEBUG] [org.gradle.api.internal.artifacts.repositories.resolver.ExternalResourceResolver] Loading http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.jar
12:22:30.253 [DEBUG] [org.gradle.api.internal.externalresource.transport.http.HttpResourceAccessor] Constructing external resource metadata: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.jar
12:22:30.253 [DEBUG] [org.gradle.api.internal.externalresource.transport.http.HttpClientHelper] Performing HTTP HEAD: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.jar
12:22:30.254 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection request: [route: {}->http://repo1.maven.org][total kept alive: 1; route allocated: 1 of 5; total allocated: 1 of 10]
12:22:30.254 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection leased: [id: 0][route: {}->http://repo1.maven.org][total kept alive: 0; route allocated: 1 of 5; total allocated: 1 of 10]
12:22:30.254 [DEBUG] [org.apache.http.impl.client.SystemDefaultHttpClient] Stale connection check
12:22:30.256 [DEBUG] [org.apache.http.client.protocol.RequestAddCookies] CookieSpec selected: best-match
12:22:30.256 [DEBUG] [org.apache.http.client.protocol.RequestAuthCache] Auth cache not set in the context
12:22:30.257 [DEBUG] [org.apache.http.client.protocol.RequestTargetAuthentication] Target auth state: UNCHALLENGED
12:22:30.257 [DEBUG] [org.apache.http.client.protocol.RequestProxyAuthentication] Proxy auth state: UNCHALLENGED
12:22:30.257 [DEBUG] [org.apache.http.impl.client.SystemDefaultHttpClient] Attempt 1 to execute request
12:22:30.257 [DEBUG] [org.apache.http.impl.conn.DefaultClientConnection] Sending request: HEAD /maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.jar HTTP/1.1
12:22:30.257 [DEBUG] [org.apache.http.headers] >> HEAD /maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.jar HTTP/1.1
12:22:30.258 [DEBUG] [org.apache.http.headers] >> Accept-Encoding: gzip,deflate
12:22:30.258 [DEBUG] [org.apache.http.headers] >> Host: repo1.maven.org
12:22:30.258 [DEBUG] [org.apache.http.headers] >> Connection: Keep-Alive
12:22:30.258 [DEBUG] [org.apache.http.headers] >> User-Agent: Gradle/1.11 (Linux;3.13.0-39-generic;amd64) (Oracle Corporation;1.8.0_25;25.25-b02)
12:22:31.005 [DEBUG] [org.apache.http.impl.conn.DefaultClientConnection] Receiving response: HTTP/1.1 404 Not Found
12:22:31.006 [DEBUG] [org.apache.http.headers] << HTTP/1.1 404 Not Found
12:22:31.006 [DEBUG] [org.apache.http.headers] << Server: nginx
12:22:31.006 [DEBUG] [org.apache.http.headers] << Content-Type: text/html
12:22:31.006 [DEBUG] [org.apache.http.headers] << Content-Length: 168
12:22:31.006 [DEBUG] [org.apache.http.headers] << Accept-Ranges: bytes
12:22:31.006 [DEBUG] [org.apache.http.headers] << Via: 1.1 varnish
12:22:31.007 [DEBUG] [org.apache.http.headers] << Accept-Ranges: bytes
12:22:31.007 [DEBUG] [org.apache.http.headers] << Date: Thu, 06 Nov 2014 11:22:30 GMT
12:22:31.007 [DEBUG] [org.apache.http.headers] << Via: 1.1 varnish
12:22:31.007 [DEBUG] [org.apache.http.headers] << Connection: keep-alive
12:22:31.007 [DEBUG] [org.apache.http.headers] << X-Served-By: cache-iad2129-IAD, cache-fra1229-FRA
12:22:31.007 [DEBUG] [org.apache.http.headers] << X-Cache: MISS, MISS
12:22:31.008 [DEBUG] [org.apache.http.headers] << X-Cache-Hits: 0, 0
12:22:31.008 [DEBUG] [org.apache.http.headers] << X-Timer: S1415272950.259906,VS0,VE112
12:22:31.008 [DEBUG] [org.apache.http.impl.client.SystemDefaultHttpClient] Connection can be kept alive indefinitely
12:22:31.008 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection [id: 0][route: {}->http://repo1.maven.org] can be kept alive indefinitely
12:22:31.009 [DEBUG] [org.apache.http.impl.conn.PoolingClientConnectionManager] Connection released: [id: 0][route: {}->http://repo1.maven.org][total kept alive: 1; route allocated: 1 of 5; total allocated: 1 of 10]
12:22:31.009 [INFO] [org.gradle.api.internal.externalresource.transport.http.HttpClientHelper] Resource missing. [HTTP HEAD: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.jar]
12:22:31.009 [DEBUG] [org.gradle.api.internal.artifacts.repositories.resolver.ExternalResourceResolver] Resource not reachable for org.apache.jmh#jmh-core;1.1.1!jmh-core.jar: res=MissingResource: http://repo1.maven.org/maven2/org/apache/jmh/jmh-core/1.1.1/jmh-core-1.1.1.jar
12:22:31.009 [DEBUG] [org.gradle.api.internal.artifacts.repositories.resolver.ExternalResourceResolver] No meta-data file nor artifact found for module 'org.apache.jmh#jmh-core;1.1.1' in repository 'MavenRepo'.
12:22:31.010 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.modulecache.DefaultModuleMetaDataCache] Recording absence of module descriptor in cache: org.apache.jmh:jmh-core:1.1.1 [changing = false]
12:22:31.013 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.testng:testng:6.8+(dependency: org.testng#testng;6.8+ {testRuntime=[default]})
12:22:31.013 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.testng:testng:6.8+' using repositories [MavenRepo]
12:22:31.014 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.testng:testng:6.8.8' for 'org.testng:testng:6.8+'
12:22:31.016 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.018 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.testng#testng;6.8.8' in 'MavenRepo'
12:22:31.018 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.testng:testng:6.8.8' from repository 'MavenRepo'
12:22:31.018 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.testng:testng:6.8.8
12:22:31.018 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> ch.qos.logback:logback-classic:1.1+(dependency: ch.qos.logback#logback-classic;1.1+ {testRuntime=[default]})
12:22:31.019 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'ch.qos.logback:logback-classic:1.1+' using repositories [MavenRepo]
12:22:31.019 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'ch.qos.logback:logback-classic:1.1.2' for 'ch.qos.logback:logback-classic:1.1+'
12:22:31.021 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.025 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'ch.qos.logback#logback-classic;1.1.2' in 'MavenRepo'
12:22:31.026 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'ch.qos.logback:logback-classic:1.1.2' from repository 'MavenRepo'
12:22:31.026 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version ch.qos.logback:logback-classic:1.1.2
12:22:31.026 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> ch.qos.logback:logback-core:1.1+(dependency: ch.qos.logback#logback-core;1.1+ {testRuntime=[default]})
12:22:31.026 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'ch.qos.logback:logback-core:1.1+' using repositories [MavenRepo]
12:22:31.027 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'ch.qos.logback:logback-core:1.1.2' for 'ch.qos.logback:logback-core:1.1+'
12:22:31.029 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.031 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'ch.qos.logback#logback-core;1.1.2' in 'MavenRepo'
12:22:31.031 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'ch.qos.logback:logback-core:1.1.2' from repository 'MavenRepo'
12:22:31.031 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version ch.qos.logback:logback-core:1.1.2
12:22:31.031 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.slf4j:jcl-over-slf4j:1.7+(dependency: org.slf4j#jcl-over-slf4j;1.7+ {testRuntime=[default]})
12:22:31.032 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.slf4j:jcl-over-slf4j:1.7+' using repositories [MavenRepo]
12:22:31.032 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.slf4j:jcl-over-slf4j:1.7.7' for 'org.slf4j:jcl-over-slf4j:1.7+'
12:22:31.034 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.036 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.slf4j#jcl-over-slf4j;1.7.7' in 'MavenRepo'
12:22:31.036 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.slf4j:jcl-over-slf4j:1.7.7' from repository 'MavenRepo'
12:22:31.036 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.slf4j:jcl-over-slf4j:1.7.7
12:22:31.036 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(testRuntime) -> org.slf4j:log4j-over-slf4j:1.7+(dependency: org.slf4j#log4j-over-slf4j;1.7+ {testRuntime=[default]})
12:22:31.037 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.slf4j:log4j-over-slf4j:1.7+' using repositories [MavenRepo]
12:22:31.037 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found resolved revision in dynamic revision cache of 'MavenRepo': Using 'org.slf4j:log4j-over-slf4j:1.7.7' for 'org.slf4j:log4j-over-slf4j:1.7+'
12:22:31.039 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.040 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.slf4j#log4j-over-slf4j;1.7.7' in 'MavenRepo'
12:22:31.041 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.slf4j:log4j-over-slf4j:1.7.7' from repository 'MavenRepo'
12:22:31.041 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.slf4j:log4j-over-slf4j:1.7.7
12:22:31.041 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.4(default).
12:22:31.042 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-lang:commons-lang:2.6(default).
12:22:31.042 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(default).
12:22:31.042 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(default) -> org.jboss.logging:jboss-logging:3.1.3.GA(dependency: org.jboss.logging#jboss-logging;3.1.3.GA {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.043 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.jboss.logging:jboss-logging:3.1.3.GA
12:22:31.043 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.jboss.logging:jboss-logging:3.1.3.GA' using repositories [MavenRepo]
12:22:31.046 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.047 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.jboss.logging#jboss-logging;3.1.3.GA' in 'MavenRepo'
12:22:31.047 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.jboss.logging:jboss-logging:3.1.3.GA' from repository 'MavenRepo'
12:22:31.048 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(default) -> org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(dependency: org.jboss.logging#jboss-logging-annotations;1.2.0.Beta1 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.048 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1
12:22:31.048 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1' using repositories [MavenRepo]
12:22:31.050 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.051 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.jboss.logging#jboss-logging-annotations;1.2.0.Beta1' in 'MavenRepo'
12:22:31.052 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1' from repository 'MavenRepo'
12:22:31.052 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final(default).
12:22:31.052 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration net.sf.ehcache:ehcache-core:2.5.2(default).
12:22:31.052 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency net.sf.ehcache:ehcache-core:2.5.2(default) -> org.slf4j:slf4j-api:1.6.1(dependency: org.slf4j#slf4j-api;1.6.1 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.052 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Found new conflicting module version org.slf4j:slf4j-api:1.6.1
12:22:31.053 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpclient:4.3.5(default).
12:22:31.053 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.apache.httpcomponents:httpclient:4.3.5(default) -> org.apache.httpcomponents:httpcore:4.3.2(dependency: org.apache.httpcomponents#httpcore;4.3.2 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.053 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.apache.httpcomponents:httpcore:4.3.2
12:22:31.053 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.apache.httpcomponents:httpcore:4.3.2' using repositories [MavenRepo]
12:22:31.056 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.057 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.apache.httpcomponents#httpcore;4.3.2' in 'MavenRepo'
12:22:31.057 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.apache.httpcomponents:httpcore:4.3.2' from repository 'MavenRepo'
12:22:31.058 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.apache.httpcomponents:httpclient:4.3.5(default) -> commons-logging:commons-logging:1.1.3(dependency: commons-logging#commons-logging;1.1.3 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.058 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version commons-logging:commons-logging:1.1.3
12:22:31.058 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'commons-logging:commons-logging:1.1.3' using repositories [MavenRepo]
12:22:31.060 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.062 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'commons-logging#commons-logging;1.1.3' in 'MavenRepo'
12:22:31.062 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'commons-logging:commons-logging:1.1.3' from repository 'MavenRepo'
12:22:31.062 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.apache.httpcomponents:httpclient:4.3.5(default) -> commons-codec:commons-codec:1.6(dependency: commons-codec#commons-codec;1.6 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.063 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Found new conflicting module version commons-codec:commons-codec:1.6
12:22:31.063 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-databind:2.4.3(default).
12:22:31.063 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency com.fasterxml.jackson.core:jackson-databind:2.4.3(default) -> com.fasterxml.jackson.core:jackson-annotations:2.4.0(dependency: com.fasterxml.jackson.core#jackson-annotations;2.4.0 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.063 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.fasterxml.jackson.core:jackson-annotations:2.4.0
12:22:31.064 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.fasterxml.jackson.core:jackson-annotations:2.4.0' using repositories [MavenRepo]
12:22:31.066 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.067 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.fasterxml.jackson.core#jackson-annotations;2.4.0' in 'MavenRepo'
12:22:31.067 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.fasterxml.jackson.core:jackson-annotations:2.4.0' from repository 'MavenRepo'
12:22:31.067 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency com.fasterxml.jackson.core:jackson-databind:2.4.3(default) -> com.fasterxml.jackson.core:jackson-core:2.4.3(dependency: com.fasterxml.jackson.core#jackson-core;2.4.3 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.068 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.fasterxml.jackson.core:jackson-core:2.4.3
12:22:31.068 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.fasterxml.jackson.core:jackson-core:2.4.3' using repositories [MavenRepo]
12:22:31.070 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.071 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.fasterxml.jackson.core#jackson-core;2.4.3' in 'MavenRepo'
12:22:31.071 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.fasterxml.jackson.core:jackson-core:2.4.3' from repository 'MavenRepo'
12:22:31.072 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.ostermiller:utils:1.07.00(default).
12:22:31.072 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(default).
12:22:31.072 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] version for org.slf4j:slf4j-api:1.7.7(default) is not selected. ignoring.
12:22:31.072 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration javax.servlet:javax.servlet-api:3.0.1(default).
12:22:31.073 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.yahoo.platform.yui:yuicompressor:2.4.7(default).
12:22:31.073 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.yahoo.platform.yui:yuicompressor:2.4.7(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.073 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.google.code.findbugs:annotations:2.0.3(default).
12:22:31.073 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-all:1.9.5(default).
12:22:31.073 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.assertj:assertj-core:1.6.1(default).
12:22:31.074 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.googlecode.catch-exception:catch-exception:1.2.0(default).
12:22:31.074 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency com.googlecode.catch-exception:catch-exception:1.2.0(default) -> org.mockito:mockito-core:1.9.5(dependency: org.mockito#mockito-core;1.9.5 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.074 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.mockito:mockito-core:1.9.5
12:22:31.074 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.mockito:mockito-core:1.9.5' using repositories [MavenRepo]
12:22:31.077 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.078 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.mockito#mockito-core;1.9.5' in 'MavenRepo'
12:22:31.078 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.mockito:mockito-core:1.9.5' from repository 'MavenRepo'
12:22:31.078 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.testng:testng:6.8.8(default).
12:22:31.079 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.testng:testng:6.8.8(default) -> org.beanshell:bsh:2.0b4(dependency: org.beanshell#bsh;2.0b4 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.079 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.beanshell:bsh:2.0b4
12:22:31.079 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.beanshell:bsh:2.0b4' using repositories [MavenRepo]
12:22:31.082 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.083 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.beanshell#bsh;2.0b4' in 'MavenRepo'
12:22:31.083 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.beanshell:bsh:2.0b4' from repository 'MavenRepo'
12:22:31.083 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.testng:testng:6.8.8(default) -> com.beust:jcommander:1.27(dependency: com.beust#jcommander;1.27 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.083 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.beust:jcommander:1.27
12:22:31.083 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.beust:jcommander:1.27' using repositories [MavenRepo]
12:22:31.086 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.087 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.beust#jcommander;1.27' in 'MavenRepo'
12:22:31.087 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.beust:jcommander:1.27' from repository 'MavenRepo'
12:22:31.087 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-classic:1.1.2(default).
12:22:31.088 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency ch.qos.logback:logback-classic:1.1.2(default) -> ch.qos.logback:logback-core:1.1.2(dependency: ch.qos.logback#logback-core;1.1.2 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.088 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency ch.qos.logback:logback-classic:1.1.2(default) -> org.slf4j:slf4j-api:1.7.6(dependency: org.slf4j#slf4j-api;1.7.6 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.088 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Found new conflicting module version org.slf4j:slf4j-api:1.7.6
12:22:31.088 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(default).
12:22:31.089 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:jcl-over-slf4j:1.7.7(default).
12:22:31.089 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.slf4j:jcl-over-slf4j:1.7.7(default) -> org.slf4j:slf4j-api:1.7.7(dependency: org.slf4j#slf4j-api;1.7.7 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.089 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:log4j-over-slf4j:1.7.7(default).
12:22:31.089 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.slf4j:log4j-over-slf4j:1.7.7(default) -> org.slf4j:slf4j-api:1.7.7(dependency: org.slf4j#slf4j-api;1.7.7 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.090 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging:3.1.3.GA(compile).
12:22:31.090 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging:3.1.3.GA(master).
12:22:31.090 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging:3.1.3.GA(runtime).
12:22:31.090 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(compile).
12:22:31.091 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(master).
12:22:31.091 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(runtime).
12:22:31.091 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpcore:4.3.2(compile).
12:22:31.091 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpcore:4.3.2(master).
12:22:31.091 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpcore:4.3.2(runtime).
12:22:31.092 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-logging:commons-logging:1.1.3(compile).
12:22:31.092 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-logging:commons-logging:1.1.3(master).
12:22:31.092 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-logging:commons-logging:1.1.3(runtime).
12:22:31.092 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-annotations:2.4.0(compile).
12:22:31.093 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-annotations:2.4.0(master).
12:22:31.093 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-annotations:2.4.0(runtime).
12:22:31.093 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-core:2.4.3(compile).
12:22:31.093 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-core:2.4.3(master).
12:22:31.093 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-core:2.4.3(runtime).
12:22:31.094 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-core:1.9.5(compile).
12:22:31.094 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.mockito:mockito-core:1.9.5(compile) -> org.hamcrest:hamcrest-core:1.1(dependency: org.hamcrest#hamcrest-core;1.1 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.094 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.hamcrest:hamcrest-core:1.1
12:22:31.094 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.hamcrest:hamcrest-core:1.1' using repositories [MavenRepo]
12:22:31.097 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.098 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.hamcrest#hamcrest-core;1.1' in 'MavenRepo'
12:22:31.098 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.hamcrest:hamcrest-core:1.1' from repository 'MavenRepo'
12:22:31.099 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.mockito:mockito-core:1.9.5(compile) -> org.objenesis:objenesis:1.0(dependency: org.objenesis#objenesis;1.0 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.099 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.objenesis:objenesis:1.0
12:22:31.099 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.objenesis:objenesis:1.0' using repositories [MavenRepo]
12:22:31.101 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.102 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.objenesis#objenesis;1.0' in 'MavenRepo'
12:22:31.103 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.objenesis:objenesis:1.0' from repository 'MavenRepo'
12:22:31.103 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-core:1.9.5(master).
12:22:31.103 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-core:1.9.5(runtime).
12:22:31.103 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.mockito:mockito-core:1.9.5(runtime) -> org.hamcrest:hamcrest-core:1.1(dependency: org.hamcrest#hamcrest-core;1.1 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.104 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency org.mockito:mockito-core:1.9.5(runtime) -> org.objenesis:objenesis:1.0(dependency: org.objenesis#objenesis;1.0 {compile=[compile(*), master(*)], runtime=[runtime(*)]})
12:22:31.104 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.beanshell:bsh:2.0b4(compile).
12:22:31.104 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.beanshell:bsh:2.0b4(master).
12:22:31.104 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.beanshell:bsh:2.0b4(runtime).
12:22:31.104 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.beust:jcommander:1.27(compile).
12:22:31.105 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.beust:jcommander:1.27(master).
12:22:31.105 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.beust:jcommander:1.27(runtime).
12:22:31.105 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(compile).
12:22:31.105 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(master).
12:22:31.105 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(runtime).
12:22:31.106 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hamcrest:hamcrest-core:1.1(compile).
12:22:31.106 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hamcrest:hamcrest-core:1.1(master).
12:22:31.106 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.objenesis:objenesis:1.0(compile).
12:22:31.106 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.objenesis:objenesis:1.0(master).
12:22:31.107 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hamcrest:hamcrest-core:1.1(runtime).
12:22:31.107 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.objenesis:objenesis:1.0(runtime).
12:22:31.107 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selected org.slf4j:slf4j-api:1.7.7 from conflicting modules [org.slf4j:slf4j-api:1.7.7, org.slf4j:slf4j-api:1.6.1, org.slf4j:slf4j-api:1.7.6].
12:22:31.108 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(default).
12:22:31.108 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(compile).
12:22:31.108 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(master).
12:22:31.108 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(runtime).
12:22:31.109 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selected commons-codec:commons-codec:1.6 from conflicting modules [commons-codec:commons-codec:1.4, commons-codec:commons-codec:1.6].
12:22:31.109 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'commons-codec:commons-codec:1.6' using repositories [MavenRepo]
12:22:31.111 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.113 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'commons-codec#commons-codec;1.6' in 'MavenRepo'
12:22:31.113 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'commons-codec:commons-codec:1.6' from repository 'MavenRepo'
12:22:31.113 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(default).
12:22:31.113 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(compile).
12:22:31.114 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(master).
12:22:31.114 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(runtime).
12:22:31.120 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching :Tools:1.0.0(testRuntime) to its parents.
12:22:31.122 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-lang:commons-lang:2.6(default) to its parents.
12:22:31.126 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(default) to its parents.
12:22:31.126 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final(default) to its parents.
12:22:31.127 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching net.sf.ehcache:ehcache-core:2.5.2(default) to its parents.
12:22:31.127 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpclient:4.3.5(default) to its parents.
12:22:31.127 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-databind:2.4.3(default) to its parents.
12:22:31.128 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.ostermiller:utils:1.07.00(default) to its parents.
12:22:31.128 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(default) to its parents.
12:22:31.128 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching javax.servlet:javax.servlet-api:3.0.1(default) to its parents.
12:22:31.128 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.yahoo.platform.yui:yuicompressor:2.4.7(default) to its parents.
12:22:31.129 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.google.code.findbugs:annotations:2.0.3(default) to its parents.
12:22:31.129 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-all:1.9.5(default) to its parents.
12:22:31.129 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.assertj:assertj-core:1.6.1(default) to its parents.
12:22:31.129 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.googlecode.catch-exception:catch-exception:1.2.0(default) to its parents.
12:22:31.130 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.testng:testng:6.8.8(default) to its parents.
12:22:31.130 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-classic:1.1.2(default) to its parents.
12:22:31.131 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(default) to its parents.
12:22:31.131 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:jcl-over-slf4j:1.7.7(default) to its parents.
12:22:31.132 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:log4j-over-slf4j:1.7.7(default) to its parents.
12:22:31.132 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging:3.1.3.GA(compile) to its parents.
12:22:31.133 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging:3.1.3.GA(master) to its parents.
12:22:31.133 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging:3.1.3.GA(runtime) to its parents.
12:22:31.133 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(compile) to its parents.
12:22:31.133 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(master) to its parents.
12:22:31.134 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(runtime) to its parents.
12:22:31.134 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpcore:4.3.2(compile) to its parents.
12:22:31.134 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpcore:4.3.2(master) to its parents.
12:22:31.135 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpcore:4.3.2(runtime) to its parents.
12:22:31.135 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-logging:commons-logging:1.1.3(compile) to its parents.
12:22:31.135 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-logging:commons-logging:1.1.3(master) to its parents.
12:22:31.135 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-logging:commons-logging:1.1.3(runtime) to its parents.
12:22:31.136 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-annotations:2.4.0(compile) to its parents.
12:22:31.136 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-annotations:2.4.0(master) to its parents.
12:22:31.136 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-annotations:2.4.0(runtime) to its parents.
12:22:31.136 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-core:2.4.3(compile) to its parents.
12:22:31.137 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-core:2.4.3(master) to its parents.
12:22:31.137 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-core:2.4.3(runtime) to its parents.
12:22:31.137 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-core:1.9.5(compile) to its parents.
12:22:31.138 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-core:1.9.5(master) to its parents.
12:22:31.138 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-core:1.9.5(runtime) to its parents.
12:22:31.138 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.beanshell:bsh:2.0b4(compile) to its parents.
12:22:31.139 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.beanshell:bsh:2.0b4(master) to its parents.
12:22:31.139 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.beanshell:bsh:2.0b4(runtime) to its parents.
12:22:31.139 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.beust:jcommander:1.27(compile) to its parents.
12:22:31.140 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.beust:jcommander:1.27(master) to its parents.
12:22:31.140 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.beust:jcommander:1.27(runtime) to its parents.
12:22:31.140 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(compile) to its parents.
12:22:31.141 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(master) to its parents.
12:22:31.141 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(runtime) to its parents.
12:22:31.141 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hamcrest:hamcrest-core:1.1(compile) to its parents.
12:22:31.141 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hamcrest:hamcrest-core:1.1(master) to its parents.
12:22:31.142 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.objenesis:objenesis:1.0(compile) to its parents.
12:22:31.142 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.objenesis:objenesis:1.0(master) to its parents.
12:22:31.142 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hamcrest:hamcrest-core:1.1(runtime) to its parents.
12:22:31.143 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.objenesis:objenesis:1.0(runtime) to its parents.
12:22:31.143 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(compile) to its parents.
12:22:31.143 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(master) to its parents.
12:22:31.144 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(runtime) to its parents.
12:22:31.144 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(default) to its parents.
12:22:31.144 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(compile) to its parents.
12:22:31.145 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(master) to its parents.
12:22:31.145 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(runtime) to its parents.
12:22:31.147 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.oldresult.TransientConfigurationResultsBuilder] Flushing resolved configuration data in Binary store in /tmp/gradle4576210569661164510.bin. Wrote root :Tools:1.0.0:testRuntime.
12:22:31.159 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.result.StreamingResolutionResultBuilder$RootFactory] Loaded resolution results (0.005 secs) from Binary store in /tmp/gradle2665861227017725645.bin (exist: true)
12:22:31.196 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.oldresult.TransientConfigurationResultsBuilder] Loaded resolved configuration results (0.008 secs) from Binary store in /tmp/gradle4576210569661164510.bin
12:22:31.344 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DefaultDependencyResolver] Resolving configuration 'detachedConfiguration1'
12:22:31.345 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.memcache.InMemoryDependencyMetadataCache] Reusing in-memory cache for repo 'MavenRepo' [e9d03b7c6586155fbee8fb2de8b5b149].
12:22:31.347 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration :Tools:1.0.0(detachedConfiguration1).
12:22:31.348 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.apache.httpcomponents:httpcore:4.3.2(dependency: org.apache.httpcomponents#httpcore;4.3.2 {detachedConfiguration1=[compile]})
12:22:31.348 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.apache.httpcomponents:httpcore:4.3.2
12:22:31.348 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.apache.httpcomponents:httpcore:4.3.2' using repositories [MavenRepo]
12:22:31.349 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.apache.httpcomponents:httpcore:4.3.2' from repository 'MavenRepo'
12:22:31.349 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.apache.httpcomponents:httpcore:4.3.2(dependency: org.apache.httpcomponents#httpcore;4.3.2 {detachedConfiguration1=[master]})
12:22:31.349 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.apache.httpcomponents:httpcore:4.3.2(dependency: org.apache.httpcomponents#httpcore;4.3.2 {detachedConfiguration1=[runtime]})
12:22:31.349 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.google.code.findbugs:annotations:2.0.3(dependency: com.google.code.findbugs#annotations;2.0.3 {detachedConfiguration1=[default]})
12:22:31.350 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.google.code.findbugs:annotations:2.0.3
12:22:31.350 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.google.code.findbugs:annotations:2.0.3' using repositories [MavenRepo]
12:22:31.350 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.google.code.findbugs:annotations:2.0.3' from repository 'MavenRepo'
12:22:31.350 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.googlecode.catch-exception:catch-exception:1.2.0(dependency: com.googlecode.catch-exception#catch-exception;1.2.0 {detachedConfiguration1=[default]})
12:22:31.350 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.googlecode.catch-exception:catch-exception:1.2.0
12:22:31.350 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.googlecode.catch-exception:catch-exception:1.2.0' using repositories [MavenRepo]
12:22:31.353 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.355 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.googlecode.catch-exception#catch-exception;1.2.0' in 'MavenRepo'
12:22:31.355 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.googlecode.catch-exception:catch-exception:1.2.0' from repository 'MavenRepo'
12:22:31.356 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.beust:jcommander:1.27(dependency: com.beust#jcommander;1.27 {detachedConfiguration1=[compile]})
12:22:31.356 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.beust:jcommander:1.27
12:22:31.356 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.beust:jcommander:1.27' using repositories [MavenRepo]
12:22:31.356 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.beust:jcommander:1.27' from repository 'MavenRepo'
12:22:31.356 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.beust:jcommander:1.27(dependency: com.beust#jcommander;1.27 {detachedConfiguration1=[master]})
12:22:31.357 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.beust:jcommander:1.27(dependency: com.beust#jcommander;1.27 {detachedConfiguration1=[runtime]})
12:22:31.357 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.slf4j:slf4j-api:1.7.7(dependency: org.slf4j#slf4j-api;1.7.7 {detachedConfiguration1=[compile]})
12:22:31.357 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.slf4j:slf4j-api:1.7.7
12:22:31.357 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.slf4j:slf4j-api:1.7.7' using repositories [MavenRepo]
12:22:31.359 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.361 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.slf4j#slf4j-api;1.7.7' in 'MavenRepo'
12:22:31.361 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.slf4j:slf4j-api:1.7.7' from repository 'MavenRepo'
12:22:31.361 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.slf4j:slf4j-api:1.7.7(dependency: org.slf4j#slf4j-api;1.7.7 {detachedConfiguration1=[master]})
12:22:31.362 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.slf4j:slf4j-api:1.7.7(dependency: org.slf4j#slf4j-api;1.7.7 {detachedConfiguration1=[runtime]})
12:22:31.362 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.slf4j:slf4j-api:1.7.7(dependency: org.slf4j#slf4j-api;1.7.7 {detachedConfiguration1=[default]})
12:22:31.362 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.testng:testng:6.8.8(dependency: org.testng#testng;6.8.8 {detachedConfiguration1=[default]})
12:22:31.362 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.testng:testng:6.8.8
12:22:31.362 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.testng:testng:6.8.8' using repositories [MavenRepo]
12:22:31.365 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.366 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.testng#testng;6.8.8' in 'MavenRepo'
12:22:31.366 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.testng:testng:6.8.8' from repository 'MavenRepo'
12:22:31.367 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.assertj:assertj-core:1.6.1(dependency: org.assertj#assertj-core;1.6.1 {detachedConfiguration1=[default]})
12:22:31.367 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.assertj:assertj-core:1.6.1
12:22:31.367 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.assertj:assertj-core:1.6.1' using repositories [MavenRepo]
12:22:31.369 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.370 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.assertj#assertj-core;1.6.1' in 'MavenRepo'
12:22:31.370 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.assertj:assertj-core:1.6.1' from repository 'MavenRepo'
12:22:31.370 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final(dependency: org.hibernate.javax.persistence#hibernate-jpa-2.1-api;1.0.0.Final {detachedConfiguration1=[default]})
12:22:31.371 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final
12:22:31.371 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final' using repositories [MavenRepo]
12:22:31.373 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.374 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.hibernate.javax.persistence#hibernate-jpa-2.1-api;1.0.0.Final' in 'MavenRepo'
12:22:31.374 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final' from repository 'MavenRepo'
12:22:31.374 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.slf4j:log4j-over-slf4j:1.7.7(dependency: org.slf4j#log4j-over-slf4j;1.7.7 {detachedConfiguration1=[default]})
12:22:31.374 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.slf4j:log4j-over-slf4j:1.7.7
12:22:31.375 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.slf4j:log4j-over-slf4j:1.7.7' using repositories [MavenRepo]
12:22:31.377 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.378 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.slf4j#log4j-over-slf4j;1.7.7' in 'MavenRepo'
12:22:31.378 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.slf4j:log4j-over-slf4j:1.7.7' from repository 'MavenRepo'
12:22:31.378 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.apache.httpcomponents:httpclient:4.3.5(dependency: org.apache.httpcomponents#httpclient;4.3.5 {detachedConfiguration1=[default]})
12:22:31.378 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.apache.httpcomponents:httpclient:4.3.5
12:22:31.378 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.apache.httpcomponents:httpclient:4.3.5' using repositories [MavenRepo]
12:22:31.380 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.382 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.apache.httpcomponents#httpclient;4.3.5' in 'MavenRepo'
12:22:31.382 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.apache.httpcomponents:httpclient:4.3.5' from repository 'MavenRepo'
12:22:31.382 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> javax.servlet:javax.servlet-api:3.0.1(dependency: javax.servlet#javax.servlet-api;3.0.1 {detachedConfiguration1=[default]})
12:22:31.382 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version javax.servlet:javax.servlet-api:3.0.1
12:22:31.382 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'javax.servlet:javax.servlet-api:3.0.1' using repositories [MavenRepo]
12:22:31.383 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'javax.servlet:javax.servlet-api:3.0.1' from repository 'MavenRepo'
12:22:31.383 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.mockito:mockito-all:1.9.5(dependency: org.mockito#mockito-all;1.9.5 {detachedConfiguration1=[default]})
12:22:31.383 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.mockito:mockito-all:1.9.5
12:22:31.383 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.mockito:mockito-all:1.9.5' using repositories [MavenRepo]
12:22:31.385 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.386 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.mockito#mockito-all;1.9.5' in 'MavenRepo'
12:22:31.386 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.mockito:mockito-all:1.9.5' from repository 'MavenRepo'
12:22:31.387 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> ch.qos.logback:logback-classic:1.1.2(dependency: ch.qos.logback#logback-classic;1.1.2 {detachedConfiguration1=[default]})
12:22:31.387 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version ch.qos.logback:logback-classic:1.1.2
12:22:31.387 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'ch.qos.logback:logback-classic:1.1.2' using repositories [MavenRepo]
12:22:31.389 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.391 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'ch.qos.logback#logback-classic;1.1.2' in 'MavenRepo'
12:22:31.392 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'ch.qos.logback:logback-classic:1.1.2' from repository 'MavenRepo'
12:22:31.392 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> net.sf.ehcache:ehcache-core:2.5.2(dependency: net.sf.ehcache#ehcache-core;2.5.2 {detachedConfiguration1=[default]})
12:22:31.392 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version net.sf.ehcache:ehcache-core:2.5.2
12:22:31.392 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'net.sf.ehcache:ehcache-core:2.5.2' using repositories [MavenRepo]
12:22:31.392 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'net.sf.ehcache:ehcache-core:2.5.2' from repository 'MavenRepo'
12:22:31.393 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.ostermiller:utils:1.07.00(dependency: org.ostermiller#utils;1.07.00 {detachedConfiguration1=[default]})
12:22:31.393 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.ostermiller:utils:1.07.00
12:22:31.393 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.ostermiller:utils:1.07.00' using repositories [MavenRepo]
12:22:31.393 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.ostermiller:utils:1.07.00' from repository 'MavenRepo'
12:22:31.393 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(dependency: org.hibernate.common#hibernate-commons-annotations;4.0.5.Final {detachedConfiguration1=[default]})
12:22:31.393 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.hibernate.common:hibernate-commons-annotations:4.0.5.Final
12:22:31.393 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.hibernate.common:hibernate-commons-annotations:4.0.5.Final' using repositories [MavenRepo]
12:22:31.395 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.396 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.hibernate.common#hibernate-commons-annotations;4.0.5.Final' in 'MavenRepo'
12:22:31.397 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.hibernate.common:hibernate-commons-annotations:4.0.5.Final' from repository 'MavenRepo'
12:22:31.397 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-codec:commons-codec:1.6(dependency: commons-codec#commons-codec;1.6 {detachedConfiguration1=[compile]})
12:22:31.397 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version commons-codec:commons-codec:1.6
12:22:31.397 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'commons-codec:commons-codec:1.6' using repositories [MavenRepo]
12:22:31.397 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'commons-codec:commons-codec:1.6' from repository 'MavenRepo'
12:22:31.397 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-codec:commons-codec:1.6(dependency: commons-codec#commons-codec;1.6 {detachedConfiguration1=[master]})
12:22:31.397 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-codec:commons-codec:1.6(dependency: commons-codec#commons-codec;1.6 {detachedConfiguration1=[runtime]})
12:22:31.398 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-codec:commons-codec:1.6(dependency: commons-codec#commons-codec;1.6 {detachedConfiguration1=[default]})
12:22:31.398 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.objenesis:objenesis:1.0(dependency: org.objenesis#objenesis;1.0 {detachedConfiguration1=[compile]})
12:22:31.398 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.objenesis:objenesis:1.0
12:22:31.398 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.objenesis:objenesis:1.0' using repositories [MavenRepo]
12:22:31.398 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.objenesis:objenesis:1.0' from repository 'MavenRepo'
12:22:31.398 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.objenesis:objenesis:1.0(dependency: org.objenesis#objenesis;1.0 {detachedConfiguration1=[master]})
12:22:31.398 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.objenesis:objenesis:1.0(dependency: org.objenesis#objenesis;1.0 {detachedConfiguration1=[runtime]})
12:22:31.399 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.beanshell:bsh:2.0b4(dependency: org.beanshell#bsh;2.0b4 {detachedConfiguration1=[compile]})
12:22:31.399 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.beanshell:bsh:2.0b4
12:22:31.399 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.beanshell:bsh:2.0b4' using repositories [MavenRepo]
12:22:31.399 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.beanshell:bsh:2.0b4' from repository 'MavenRepo'
12:22:31.399 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.beanshell:bsh:2.0b4(dependency: org.beanshell#bsh;2.0b4 {detachedConfiguration1=[master]})
12:22:31.399 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.beanshell:bsh:2.0b4(dependency: org.beanshell#bsh;2.0b4 {detachedConfiguration1=[runtime]})
12:22:31.400 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.fasterxml.jackson.core:jackson-core:2.4.3(dependency: com.fasterxml.jackson.core#jackson-core;2.4.3 {detachedConfiguration1=[compile]})
12:22:31.400 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.fasterxml.jackson.core:jackson-core:2.4.3
12:22:31.400 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.fasterxml.jackson.core:jackson-core:2.4.3' using repositories [MavenRepo]
12:22:31.400 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.fasterxml.jackson.core:jackson-core:2.4.3' from repository 'MavenRepo'
12:22:31.400 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.fasterxml.jackson.core:jackson-core:2.4.3(dependency: com.fasterxml.jackson.core#jackson-core;2.4.3 {detachedConfiguration1=[master]})
12:22:31.400 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.fasterxml.jackson.core:jackson-core:2.4.3(dependency: com.fasterxml.jackson.core#jackson-core;2.4.3 {detachedConfiguration1=[runtime]})
12:22:31.400 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> ch.qos.logback:logback-core:1.1.2(dependency: ch.qos.logback#logback-core;1.1.2 {detachedConfiguration1=[compile]})
12:22:31.401 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version ch.qos.logback:logback-core:1.1.2
12:22:31.401 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'ch.qos.logback:logback-core:1.1.2' using repositories [MavenRepo]
12:22:31.403 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.405 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'ch.qos.logback#logback-core;1.1.2' in 'MavenRepo'
12:22:31.405 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'ch.qos.logback:logback-core:1.1.2' from repository 'MavenRepo'
12:22:31.405 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> ch.qos.logback:logback-core:1.1.2(dependency: ch.qos.logback#logback-core;1.1.2 {detachedConfiguration1=[master]})
12:22:31.405 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> ch.qos.logback:logback-core:1.1.2(dependency: ch.qos.logback#logback-core;1.1.2 {detachedConfiguration1=[runtime]})
12:22:31.406 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> ch.qos.logback:logback-core:1.1.2(dependency: ch.qos.logback#logback-core;1.1.2 {detachedConfiguration1=[default]})
12:22:31.406 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.fasterxml.jackson.core:jackson-databind:2.4.3(dependency: com.fasterxml.jackson.core#jackson-databind;2.4.3 {detachedConfiguration1=[default]})
12:22:31.406 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.fasterxml.jackson.core:jackson-databind:2.4.3
12:22:31.406 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.fasterxml.jackson.core:jackson-databind:2.4.3' using repositories [MavenRepo]
12:22:31.409 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.410 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'com.fasterxml.jackson.core#jackson-databind;2.4.3' in 'MavenRepo'
12:22:31.410 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.fasterxml.jackson.core:jackson-databind:2.4.3' from repository 'MavenRepo'
12:22:31.411 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.jboss.logging:jboss-logging:3.1.3.GA(dependency: org.jboss.logging#jboss-logging;3.1.3.GA {detachedConfiguration1=[compile]})
12:22:31.411 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.jboss.logging:jboss-logging:3.1.3.GA
12:22:31.411 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.jboss.logging:jboss-logging:3.1.3.GA' using repositories [MavenRepo]
12:22:31.411 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.jboss.logging:jboss-logging:3.1.3.GA' from repository 'MavenRepo'
12:22:31.411 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.jboss.logging:jboss-logging:3.1.3.GA(dependency: org.jboss.logging#jboss-logging;3.1.3.GA {detachedConfiguration1=[master]})
12:22:31.412 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.jboss.logging:jboss-logging:3.1.3.GA(dependency: org.jboss.logging#jboss-logging;3.1.3.GA {detachedConfiguration1=[runtime]})
12:22:31.412 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.slf4j:jcl-over-slf4j:1.7.7(dependency: org.slf4j#jcl-over-slf4j;1.7.7 {detachedConfiguration1=[default]})
12:22:31.412 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.slf4j:jcl-over-slf4j:1.7.7
12:22:31.412 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.slf4j:jcl-over-slf4j:1.7.7' using repositories [MavenRepo]
12:22:31.414 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.parser.IvyXmlModuleDescriptorParser] post 1.3 ivy file: using exact as default matcher
12:22:31.416 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Using cached module metadata for module 'org.slf4j#jcl-over-slf4j;1.7.7' in 'MavenRepo'
12:22:31.416 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.slf4j:jcl-over-slf4j:1.7.7' from repository 'MavenRepo'
12:22:31.416 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.fasterxml.jackson.core:jackson-annotations:2.4.0(dependency: com.fasterxml.jackson.core#jackson-annotations;2.4.0 {detachedConfiguration1=[compile]})
12:22:31.416 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.fasterxml.jackson.core:jackson-annotations:2.4.0
12:22:31.417 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.fasterxml.jackson.core:jackson-annotations:2.4.0' using repositories [MavenRepo]
12:22:31.417 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.fasterxml.jackson.core:jackson-annotations:2.4.0' from repository 'MavenRepo'
12:22:31.417 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.fasterxml.jackson.core:jackson-annotations:2.4.0(dependency: com.fasterxml.jackson.core#jackson-annotations;2.4.0 {detachedConfiguration1=[master]})
12:22:31.417 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.fasterxml.jackson.core:jackson-annotations:2.4.0(dependency: com.fasterxml.jackson.core#jackson-annotations;2.4.0 {detachedConfiguration1=[runtime]})
12:22:31.417 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-lang:commons-lang:2.6(dependency: commons-lang#commons-lang;2.6 {detachedConfiguration1=[default]})
12:22:31.418 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version commons-lang:commons-lang:2.6
12:22:31.418 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'commons-lang:commons-lang:2.6' using repositories [MavenRepo]
12:22:31.418 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'commons-lang:commons-lang:2.6' from repository 'MavenRepo'
12:22:31.418 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> com.yahoo.platform.yui:yuicompressor:2.4.7(dependency: com.yahoo.platform.yui#yuicompressor;2.4.7 {detachedConfiguration1=[default]})
12:22:31.418 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version com.yahoo.platform.yui:yuicompressor:2.4.7
12:22:31.419 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'com.yahoo.platform.yui:yuicompressor:2.4.7' using repositories [MavenRepo]
12:22:31.419 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'com.yahoo.platform.yui:yuicompressor:2.4.7' from repository 'MavenRepo'
12:22:31.419 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(dependency: org.jboss.logging#jboss-logging-annotations;1.2.0.Beta1 {detachedConfiguration1=[compile]})
12:22:31.419 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1
12:22:31.419 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1' using repositories [MavenRepo]
12:22:31.419 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1' from repository 'MavenRepo'
12:22:31.420 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(dependency: org.jboss.logging#jboss-logging-annotations;1.2.0.Beta1 {detachedConfiguration1=[master]})
12:22:31.420 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(dependency: org.jboss.logging#jboss-logging-annotations;1.2.0.Beta1 {detachedConfiguration1=[runtime]})
12:22:31.420 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.hamcrest:hamcrest-core:1.1(dependency: org.hamcrest#hamcrest-core;1.1 {detachedConfiguration1=[compile]})
12:22:31.420 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.hamcrest:hamcrest-core:1.1
12:22:31.420 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.hamcrest:hamcrest-core:1.1' using repositories [MavenRepo]
12:22:31.421 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.hamcrest:hamcrest-core:1.1' from repository 'MavenRepo'
12:22:31.421 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.hamcrest:hamcrest-core:1.1(dependency: org.hamcrest#hamcrest-core;1.1 {detachedConfiguration1=[master]})
12:22:31.421 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.hamcrest:hamcrest-core:1.1(dependency: org.hamcrest#hamcrest-core;1.1 {detachedConfiguration1=[runtime]})
12:22:31.421 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.mockito:mockito-core:1.9.5(dependency: org.mockito#mockito-core;1.9.5 {detachedConfiguration1=[compile]})
12:22:31.421 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version org.mockito:mockito-core:1.9.5
12:22:31.422 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'org.mockito:mockito-core:1.9.5' using repositories [MavenRepo]
12:22:31.422 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'org.mockito:mockito-core:1.9.5' from repository 'MavenRepo'
12:22:31.422 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.mockito:mockito-core:1.9.5(dependency: org.mockito#mockito-core;1.9.5 {detachedConfiguration1=[master]})
12:22:31.422 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> org.mockito:mockito-core:1.9.5(dependency: org.mockito#mockito-core;1.9.5 {detachedConfiguration1=[runtime]})
12:22:31.422 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-logging:commons-logging:1.1.3(dependency: commons-logging#commons-logging;1.1.3 {detachedConfiguration1=[compile]})
12:22:31.423 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Selecting new module version commons-logging:commons-logging:1.1.3
12:22:31.423 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Attempting to resolve module 'commons-logging:commons-logging:1.1.3' using repositories [MavenRepo]
12:22:31.423 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.UserResolverChain] Using module 'commons-logging:commons-logging:1.1.3' from repository 'MavenRepo'
12:22:31.423 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-logging:commons-logging:1.1.3(dependency: commons-logging#commons-logging;1.1.3 {detachedConfiguration1=[master]})
12:22:31.423 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting dependency :Tools:1.0.0(detachedConfiguration1) -> commons-logging:commons-logging:1.1.3(dependency: commons-logging#commons-logging;1.1.3 {detachedConfiguration1=[runtime]})
12:22:31.424 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpcore:4.3.2(compile).
12:22:31.424 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.apache.httpcomponents:httpcore:4.3.2(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.424 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpcore:4.3.2(master).
12:22:31.424 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.apache.httpcomponents:httpcore:4.3.2(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.424 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpcore:4.3.2(runtime).
12:22:31.424 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.apache.httpcomponents:httpcore:4.3.2(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.424 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.google.code.findbugs:annotations:2.0.3(default).
12:22:31.425 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.google.code.findbugs:annotations:2.0.3(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.425 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.googlecode.catch-exception:catch-exception:1.2.0(default).
12:22:31.425 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.googlecode.catch-exception:catch-exception:1.2.0(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.425 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.beust:jcommander:1.27(compile).
12:22:31.425 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.beust:jcommander:1.27(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.425 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.beust:jcommander:1.27(master).
12:22:31.426 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.beust:jcommander:1.27(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.426 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.beust:jcommander:1.27(runtime).
12:22:31.426 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.beust:jcommander:1.27(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.426 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(compile).
12:22:31.426 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.slf4j:slf4j-api:1.7.7(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.426 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(master).
12:22:31.427 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.slf4j:slf4j-api:1.7.7(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.427 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(runtime).
12:22:31.427 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.slf4j:slf4j-api:1.7.7(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.427 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:slf4j-api:1.7.7(default).
12:22:31.427 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.slf4j:slf4j-api:1.7.7(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.427 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.testng:testng:6.8.8(default).
12:22:31.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.testng:testng:6.8.8(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.assertj:assertj-core:1.6.1(default).
12:22:31.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.assertj:assertj-core:1.6.1(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final(default).
12:22:31.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:log4j-over-slf4j:1.7.7(default).
12:22:31.428 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.slf4j:log4j-over-slf4j:1.7.7(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.429 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.apache.httpcomponents:httpclient:4.3.5(default).
12:22:31.429 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.apache.httpcomponents:httpclient:4.3.5(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.429 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration javax.servlet:javax.servlet-api:3.0.1(default).
12:22:31.429 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] javax.servlet:javax.servlet-api:3.0.1(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.429 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-all:1.9.5(default).
12:22:31.429 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.mockito:mockito-all:1.9.5(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.429 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-classic:1.1.2(default).
12:22:31.430 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] ch.qos.logback:logback-classic:1.1.2(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.430 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration net.sf.ehcache:ehcache-core:2.5.2(default).
12:22:31.430 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] net.sf.ehcache:ehcache-core:2.5.2(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.430 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.ostermiller:utils:1.07.00(default).
12:22:31.430 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.ostermiller:utils:1.07.00(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.430 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(default).
12:22:31.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(compile).
12:22:31.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-codec:commons-codec:1.6(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(master).
12:22:31.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-codec:commons-codec:1.6(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(runtime).
12:22:31.431 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-codec:commons-codec:1.6(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.432 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-codec:commons-codec:1.6(default).
12:22:31.432 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-codec:commons-codec:1.6(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.432 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.objenesis:objenesis:1.0(compile).
12:22:31.432 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.objenesis:objenesis:1.0(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.432 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.objenesis:objenesis:1.0(master).
12:22:31.432 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.objenesis:objenesis:1.0(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.432 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.objenesis:objenesis:1.0(runtime).
12:22:31.433 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.objenesis:objenesis:1.0(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.433 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.beanshell:bsh:2.0b4(compile).
12:22:31.433 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.beanshell:bsh:2.0b4(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.433 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.beanshell:bsh:2.0b4(master).
12:22:31.433 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.beanshell:bsh:2.0b4(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.433 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.beanshell:bsh:2.0b4(runtime).
12:22:31.433 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.beanshell:bsh:2.0b4(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.434 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-core:2.4.3(compile).
12:22:31.434 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.fasterxml.jackson.core:jackson-core:2.4.3(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.434 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-core:2.4.3(master).
12:22:31.434 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.fasterxml.jackson.core:jackson-core:2.4.3(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.434 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-core:2.4.3(runtime).
12:22:31.434 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.fasterxml.jackson.core:jackson-core:2.4.3(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.434 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(compile).
12:22:31.435 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] ch.qos.logback:logback-core:1.1.2(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.435 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(master).
12:22:31.435 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] ch.qos.logback:logback-core:1.1.2(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.435 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(runtime).
12:22:31.435 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] ch.qos.logback:logback-core:1.1.2(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.435 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration ch.qos.logback:logback-core:1.1.2(default).
12:22:31.435 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] ch.qos.logback:logback-core:1.1.2(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.436 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-databind:2.4.3(default).
12:22:31.436 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.fasterxml.jackson.core:jackson-databind:2.4.3(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.436 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging:3.1.3.GA(compile).
12:22:31.436 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.jboss.logging:jboss-logging:3.1.3.GA(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.436 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging:3.1.3.GA(master).
12:22:31.436 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.jboss.logging:jboss-logging:3.1.3.GA(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.436 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging:3.1.3.GA(runtime).
12:22:31.437 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.jboss.logging:jboss-logging:3.1.3.GA(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.437 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.slf4j:jcl-over-slf4j:1.7.7(default).
12:22:31.437 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.slf4j:jcl-over-slf4j:1.7.7(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.437 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-annotations:2.4.0(compile).
12:22:31.437 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.fasterxml.jackson.core:jackson-annotations:2.4.0(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.437 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-annotations:2.4.0(master).
12:22:31.438 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.fasterxml.jackson.core:jackson-annotations:2.4.0(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.438 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.fasterxml.jackson.core:jackson-annotations:2.4.0(runtime).
12:22:31.438 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.fasterxml.jackson.core:jackson-annotations:2.4.0(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.438 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-lang:commons-lang:2.6(default).
12:22:31.438 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-lang:commons-lang:2.6(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.438 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration com.yahoo.platform.yui:yuicompressor:2.4.7(default).
12:22:31.438 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] com.yahoo.platform.yui:yuicompressor:2.4.7(default) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.439 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(compile).
12:22:31.439 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.439 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(master).
12:22:31.439 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.439 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(runtime).
12:22:31.439 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.439 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hamcrest:hamcrest-core:1.1(compile).
12:22:31.440 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.hamcrest:hamcrest-core:1.1(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.440 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hamcrest:hamcrest-core:1.1(master).
12:22:31.440 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.hamcrest:hamcrest-core:1.1(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.440 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.hamcrest:hamcrest-core:1.1(runtime).
12:22:31.440 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.hamcrest:hamcrest-core:1.1(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.440 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-core:1.9.5(compile).
12:22:31.441 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.mockito:mockito-core:1.9.5(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.441 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-core:1.9.5(master).
12:22:31.441 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.mockito:mockito-core:1.9.5(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.441 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration org.mockito:mockito-core:1.9.5(runtime).
12:22:31.441 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] org.mockito:mockito-core:1.9.5(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.441 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-logging:commons-logging:1.1.3(compile).
12:22:31.442 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-logging:commons-logging:1.1.3(compile) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.442 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-logging:commons-logging:1.1.3(master).
12:22:31.442 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-logging:commons-logging:1.1.3(master) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.442 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Visiting configuration commons-logging:commons-logging:1.1.3(runtime).
12:22:31.442 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] commons-logging:commons-logging:1.1.3(runtime) has no transitive incoming edges. ignoring outgoing edges.
12:22:31.443 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching :Tools:1.0.0(detachedConfiguration1) to its parents.
12:22:31.444 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpcore:4.3.2(compile) to its parents.
12:22:31.445 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpcore:4.3.2(master) to its parents.
12:22:31.445 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpcore:4.3.2(runtime) to its parents.
12:22:31.445 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.google.code.findbugs:annotations:2.0.3(default) to its parents.
12:22:31.446 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.googlecode.catch-exception:catch-exception:1.2.0(default) to its parents.
12:22:31.446 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.beust:jcommander:1.27(compile) to its parents.
12:22:31.446 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.beust:jcommander:1.27(master) to its parents.
12:22:31.447 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.beust:jcommander:1.27(runtime) to its parents.
12:22:31.447 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(compile) to its parents.
12:22:31.447 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(master) to its parents.
12:22:31.447 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(runtime) to its parents.
12:22:31.448 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:slf4j-api:1.7.7(default) to its parents.
12:22:31.448 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.testng:testng:6.8.8(default) to its parents.
12:22:31.448 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.assertj:assertj-core:1.6.1(default) to its parents.
12:22:31.448 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final(default) to its parents.
12:22:31.449 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:log4j-over-slf4j:1.7.7(default) to its parents.
12:22:31.449 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.apache.httpcomponents:httpclient:4.3.5(default) to its parents.
12:22:31.449 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching javax.servlet:javax.servlet-api:3.0.1(default) to its parents.
12:22:31.450 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-all:1.9.5(default) to its parents.
12:22:31.450 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-classic:1.1.2(default) to its parents.
12:22:31.450 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching net.sf.ehcache:ehcache-core:2.5.2(default) to its parents.
12:22:31.451 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.ostermiller:utils:1.07.00(default) to its parents.
12:22:31.451 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hibernate.common:hibernate-commons-annotations:4.0.5.Final(default) to its parents.
12:22:31.451 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(compile) to its parents.
12:22:31.451 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(master) to its parents.
12:22:31.451 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(runtime) to its parents.
12:22:31.452 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-codec:commons-codec:1.6(default) to its parents.
12:22:31.452 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.objenesis:objenesis:1.0(compile) to its parents.
12:22:31.452 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.objenesis:objenesis:1.0(master) to its parents.
12:22:31.452 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.objenesis:objenesis:1.0(runtime) to its parents.
12:22:31.452 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.beanshell:bsh:2.0b4(compile) to its parents.
12:22:31.453 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.beanshell:bsh:2.0b4(master) to its parents.
12:22:31.453 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.beanshell:bsh:2.0b4(runtime) to its parents.
12:22:31.453 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-core:2.4.3(compile) to its parents.
12:22:31.453 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-core:2.4.3(master) to its parents.
12:22:31.453 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-core:2.4.3(runtime) to its parents.
12:22:31.453 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(compile) to its parents.
12:22:31.454 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(master) to its parents.
12:22:31.454 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(runtime) to its parents.
12:22:31.454 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching ch.qos.logback:logback-core:1.1.2(default) to its parents.
12:22:31.454 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-databind:2.4.3(default) to its parents.
12:22:31.455 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging:3.1.3.GA(compile) to its parents.
12:22:31.455 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging:3.1.3.GA(master) to its parents.
12:22:31.455 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging:3.1.3.GA(runtime) to its parents.
12:22:31.455 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.slf4j:jcl-over-slf4j:1.7.7(default) to its parents.
12:22:31.456 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-annotations:2.4.0(compile) to its parents.
12:22:31.456 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-annotations:2.4.0(master) to its parents.
12:22:31.456 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.fasterxml.jackson.core:jackson-annotations:2.4.0(runtime) to its parents.
12:22:31.456 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-lang:commons-lang:2.6(default) to its parents.
12:22:31.457 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching com.yahoo.platform.yui:yuicompressor:2.4.7(default) to its parents.
12:22:31.457 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(compile) to its parents.
12:22:31.457 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(master) to its parents.
12:22:31.457 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1(runtime) to its parents.
12:22:31.458 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hamcrest:hamcrest-core:1.1(compile) to its parents.
12:22:31.458 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hamcrest:hamcrest-core:1.1(master) to its parents.
12:22:31.458 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.hamcrest:hamcrest-core:1.1(runtime) to its parents.
12:22:31.458 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-core:1.9.5(compile) to its parents.
12:22:31.458 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-core:1.9.5(master) to its parents.
12:22:31.459 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching org.mockito:mockito-core:1.9.5(runtime) to its parents.
12:22:31.459 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-logging:commons-logging:1.1.3(compile) to its parents.
12:22:31.459 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-logging:commons-logging:1.1.3(master) to its parents.
12:22:31.459 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.DependencyGraphBuilder] Attaching commons-logging:commons-logging:1.1.3(runtime) to its parents.
12:22:31.460 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.oldresult.TransientConfigurationResultsBuilder] Flushing resolved configuration data in Binary store in /tmp/gradle4576210569661164510.bin. Wrote root :Tools:1.0.0:detachedConfiguration1.
12:22:31.472 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache artifact-at-repository.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/artifact-at-repository.bin)
12:22:31.475 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.apache.httpcomponents:httpcore:4.3.2:httpcore-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.apache.httpcomponents/httpcore/4.3.2/4809f38359edeea9487f747e09aa58ec8d3a54c5/httpcore-4.3.2-sources.jar
12:22:31.476 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.google.code.findbugs:annotations:2.0.3:annotations-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.google.code.findbugs/annotations/2.0.3/936f4430478909ed7b138d42f9ad73c919a87b26/annotations-2.0.3-sources.jar
12:22:31.477 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.googlecode.catch-exception:catch-exception:1.2.0:catch-exception-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.googlecode.catch-exception/catch-exception/1.2.0/c51ccdb4523d1b62f9d18e24e88e7b7e888295f4/catch-exception-1.2.0-sources.jar
12:22:31.477 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.beust:jcommander:1.27:jcommander-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.beust/jcommander/1.27/fae697ac46477a5227a5ee14f8f3a1270a4627b0/jcommander-1.27-sources.jar
12:22:31.478 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.slf4j:slf4j-api:1.7.7:slf4j-api-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.slf4j/slf4j-api/1.7.7/acd62e31cc314266e73eebed0b6dd7ea974a0ed/slf4j-api-1.7.7-sources.jar
12:22:31.479 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.testng:testng:6.8.8:testng-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.testng/testng/6.8.8/2e40f493e5db82f4586d24034b4dd931d0b7f9dc/testng-6.8.8-sources.jar
12:22:31.479 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.assertj:assertj-core:1.6.1:assertj-core-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.assertj/assertj-core/1.6.1/631f89e7050cf8ac2ba25d68f0434b80731bf2f2/assertj-core-1.6.1-sources.jar
12:22:31.480 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final:hibernate-jpa-2.1-api-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.hibernate.javax.persistence/hibernate-jpa-2.1-api/1.0.0.Final/33fbaa7276b774ef0925f541640f6ff23fbc62dc/hibernate-jpa-2.1-api-1.0.0.Final-sources.jar
12:22:31.480 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.slf4j:log4j-over-slf4j:1.7.7:log4j-over-slf4j-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.slf4j/log4j-over-slf4j/1.7.7/307e030bc37259ad60dce614530d698ac74c4dc/log4j-over-slf4j-1.7.7-sources.jar
12:22:31.480 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.apache.httpcomponents:httpclient:4.3.5:httpclient-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.apache.httpcomponents/httpclient/4.3.5/9351922abd5e659dcbacb1d38b81d8069bac797b/httpclient-4.3.5-sources.jar
12:22:31.481 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'javax.servlet:javax.servlet-api:3.0.1:javax.servlet-api-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/javax.servlet/javax.servlet-api/3.0.1/1952f91d84016a39e31346c9d18bd8c9c4a128a/javax.servlet-api-3.0.1-sources.jar
12:22:31.482 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.mockito:mockito-all:1.9.5:mockito-all-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.mockito/mockito-all/1.9.5/b8086d4e4daa361fc746abea62f77a2ae55d3ed9/mockito-all-1.9.5-sources.jar
12:22:31.482 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'ch.qos.logback:logback-classic:1.1.2:logback-classic-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-classic/1.1.2/decd76e2c461157804473468bbdc6b8eb6d6121b/logback-classic-1.1.2-sources.jar
12:22:31.483 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'net.sf.ehcache:ehcache-core:2.5.2:ehcache-core-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/net.sf.ehcache/ehcache-core/2.5.2/c75228238197040d23095cfadfd0be3b62b91a1d/ehcache-core-2.5.2-sources.jar
12:22:31.483 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.ostermiller:utils:1.07.00:utils-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.ostermiller/utils/1.07.00/586774ee4b8409b6835621bae2186d9b54d1c36a/utils-1.07.00-sources.jar
12:22:31.484 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.hibernate.common:hibernate-commons-annotations:4.0.5.Final:hibernate-commons-annotations-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.hibernate.common/hibernate-commons-annotations/4.0.5.Final/98339e44338b43de01cb5ae0b169447d17ae00/hibernate-commons-annotations-4.0.5.Final-sources.jar
12:22:31.485 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'commons-codec:commons-codec:1.6:commons-codec-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/commons-codec/commons-codec/1.6/61e9f9d11fe3e12ec62f633006e99d75fd7f19c8/commons-codec-1.6-sources.jar
12:22:31.485 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.objenesis:objenesis:1.0:objenesis-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.objenesis/objenesis/1.0/b10c90e57b7bb985a7b6a704769428fe5c2a732c/objenesis-1.0-sources.jar
12:22:31.486 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Detected non-existence of artifact 'org.beanshell:bsh:2.0b4:bsh-sources.jar' in resolver cache
12:22:31.486 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.fasterxml.jackson.core:jackson-core:2.4.3:jackson-core-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-core/2.4.3/31c4e5d7964ffce69d56777a167fb16af28440b7/jackson-core-2.4.3-sources.jar
12:22:31.487 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'ch.qos.logback:logback-core:1.1.2:logback-core-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-core/1.1.2/502e1c764542fe747896c1dc04f023acfe0e5cbc/logback-core-1.1.2-sources.jar
12:22:31.487 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.fasterxml.jackson.core:jackson-databind:2.4.3:jackson-databind-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-databind/2.4.3/622aa2565d182ed609b25c4826124a5b687048a7/jackson-databind-2.4.3-sources.jar
12:22:31.488 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.jboss.logging:jboss-logging:3.1.3.GA:jboss-logging-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.jboss.logging/jboss-logging/3.1.3.GA/4d2c4df2c1a1b2d2f9201f6d580d7d19ccf8cbdf/jboss-logging-3.1.3.GA-sources.jar
12:22:31.488 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.slf4j:jcl-over-slf4j:1.7.7:jcl-over-slf4j-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.slf4j/jcl-over-slf4j/1.7.7/b5a646b81b899e930f8600c5f45766dd82743d93/jcl-over-slf4j-1.7.7-sources.jar
12:22:31.489 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.fasterxml.jackson.core:jackson-annotations:2.4.0:jackson-annotations-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-annotations/2.4.0/7fcf3182660e0be49c7448e13f91e655b2cba9bf/jackson-annotations-2.4.0-sources.jar
12:22:31.489 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'commons-lang:commons-lang:2.6:commons-lang-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/commons-lang/commons-lang/2.6/67313d715fbf0ea4fd0bdb69217fb77f807a8ce5/commons-lang-2.6-sources.jar
12:22:31.490 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.yahoo.platform.yui:yuicompressor:2.4.7:yuicompressor-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.yahoo.platform.yui/yuicompressor/2.4.7/1f20ab5146d5983b127477fe713184c84c27a882/yuicompressor-2.4.7-sources.jar
12:22:31.490 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1:jboss-logging-annotations-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.jboss.logging/jboss-logging-annotations/1.2.0.Beta1/360b782ab568672ea968726ddf209e753c860e1a/jboss-logging-annotations-1.2.0.Beta1-sources.jar
12:22:31.491 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.hamcrest:hamcrest-core:1.1:hamcrest-core-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.hamcrest/hamcrest-core/1.1/2ccf1154d1a8936042a8a742dc3e611d02ac7213/hamcrest-core-1.1-sources.jar
12:22:31.491 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.mockito:mockito-core:1.9.5:mockito-core-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.mockito/mockito-core/1.9.5/46f703fb4266140c544d48a189cb25947eb6333e/mockito-core-1.9.5-sources.jar
12:22:31.492 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'commons-logging:commons-logging:1.1.3:commons-logging-sources.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/commons-logging/commons-logging/1.1.3/28bb0405fddaf04f15058fbfbe01fe2780d7d3b6/commons-logging-1.1.3-sources.jar
12:22:31.503 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'commons-lang:commons-lang:2.6:commons-lang.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/commons-lang/commons-lang/2.6/ce1edb914c94ebc388f086c6827e8bdeec71ac2/commons-lang-2.6.jar
12:22:31.504 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.hibernate.common:hibernate-commons-annotations:4.0.5.Final:hibernate-commons-annotations.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.hibernate.common/hibernate-commons-annotations/4.0.5.Final/2a581b9edb8168e45060d8bad8b7f46712d2c52c/hibernate-commons-annotations-4.0.5.Final.jar
12:22:31.504 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final:hibernate-jpa-2.1-api.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.hibernate.javax.persistence/hibernate-jpa-2.1-api/1.0.0.Final/5e731d961297e5a07290bfaf3db1fbc8bbbf405a/hibernate-jpa-2.1-api-1.0.0.Final.jar
12:22:31.505 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'net.sf.ehcache:ehcache-core:2.5.2:ehcache-core.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/net.sf.ehcache/ehcache-core/2.5.2/31cbafefbecb34a1081939230a5bc6f13a1c55db/ehcache-core-2.5.2.jar
12:22:31.505 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.apache.httpcomponents:httpclient:4.3.5:httpclient.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.apache.httpcomponents/httpclient/4.3.5/9783d89b8eea20a517a4afc5f979bd2882b54c44/httpclient-4.3.5.jar
12:22:31.506 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.fasterxml.jackson.core:jackson-databind:2.4.3:jackson-databind.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-databind/2.4.3/feff63199be7b8f495c2f3e2096dcb6bd5e5b0b3/jackson-databind-2.4.3.jar
12:22:31.506 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.ostermiller:utils:1.07.00:utils.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.ostermiller/utils/1.07.00/a8828217b2dd0507fbe9e9d0b2981acfb908b590/utils-1.07.00.jar
12:22:31.507 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.slf4j:slf4j-api:1.7.7:slf4j-api.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.slf4j/slf4j-api/1.7.7/2b8019b6249bb05d81d3a3094e468753e2b21311/slf4j-api-1.7.7.jar
12:22:31.507 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'javax.servlet:javax.servlet-api:3.0.1:javax.servlet-api.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/javax.servlet/javax.servlet-api/3.0.1/6bf0ebb7efd993e222fc1112377b5e92a13b38dd/javax.servlet-api-3.0.1.jar
12:22:31.507 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.yahoo.platform.yui:yuicompressor:2.4.7:yuicompressor.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.yahoo.platform.yui/yuicompressor/2.4.7/8ecb802ac0a33d3589ec2309e926f1a9ef64c4df/yuicompressor-2.4.7.jar
12:22:31.508 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.google.code.findbugs:annotations:2.0.3:annotations.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.google.code.findbugs/annotations/2.0.3/191383fa0deb88f393558eec231b206edc23aba0/annotations-2.0.3.jar
12:22:31.508 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.mockito:mockito-all:1.9.5:mockito-all.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.mockito/mockito-all/1.9.5/79a8984096fc6591c1e3690e07d41be506356fa5/mockito-all-1.9.5.jar
12:22:31.509 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.assertj:assertj-core:1.6.1:assertj-core.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.assertj/assertj-core/1.6.1/5db13d0ae30dca5157f0dea60f78640cf039cafe/assertj-core-1.6.1.jar
12:22:31.509 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.googlecode.catch-exception:catch-exception:1.2.0:catch-exception.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.googlecode.catch-exception/catch-exception/1.2.0/f2d1a395d91b4c024b9cc6a0946cfb10199df0a0/catch-exception-1.2.0.jar
12:22:31.509 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.testng:testng:6.8.8:testng.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.testng/testng/6.8.8/c4fb20cf89d278d71667d7273fa6e6cff8b97ca9/testng-6.8.8.jar
12:22:31.510 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'ch.qos.logback:logback-classic:1.1.2:logback-classic.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-classic/1.1.2/b316e9737eea25e9ddd6d88eaeee76878045c6b2/logback-classic-1.1.2.jar
12:22:31.510 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'ch.qos.logback:logback-core:1.1.2:logback-core.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/ch.qos.logback/logback-core/1.1.2/2d23694879c2c12f125dac5076bdfd5d771cc4cb/logback-core-1.1.2.jar
12:22:31.511 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.slf4j:jcl-over-slf4j:1.7.7:jcl-over-slf4j.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.slf4j/jcl-over-slf4j/1.7.7/56003dcd0a31deea6391b9e2ef2f2dc90b205a92/jcl-over-slf4j-1.7.7.jar
12:22:31.511 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.slf4j:log4j-over-slf4j:1.7.7:log4j-over-slf4j.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.slf4j/log4j-over-slf4j/1.7.7/d521cb26a9c4407caafcec302e7804b048b07cea/log4j-over-slf4j-1.7.7.jar
12:22:31.512 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'commons-codec:commons-codec:1.6:commons-codec.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/commons-codec/commons-codec/1.6/b7f0fc8f61ecadeb3695f0b9464755eee44374d4/commons-codec-1.6.jar
12:22:31.512 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.jboss.logging:jboss-logging:3.1.3.GA:jboss-logging.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.jboss.logging/jboss-logging/3.1.3.GA/64499e907f19e5e1b3fdc02f81440c1832fe3545/jboss-logging-3.1.3.GA.jar
12:22:31.513 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.jboss.logging:jboss-logging-annotations:1.2.0.Beta1:jboss-logging-annotations.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.jboss.logging/jboss-logging-annotations/1.2.0.Beta1/2f437f37bb265d9f8f1392823dbca12d2bec06d6/jboss-logging-annotations-1.2.0.Beta1.jar
12:22:31.513 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.apache.httpcomponents:httpcore:4.3.2:httpcore.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.apache.httpcomponents/httpcore/4.3.2/31fbbff1ddbf98f3aa7377c94d33b0447c646b6e/httpcore-4.3.2.jar
12:22:31.513 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'commons-logging:commons-logging:1.1.3:commons-logging.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/commons-logging/commons-logging/1.1.3/f6f66e966c70a83ffbdb6f17a0919eaf7c8aca7f/commons-logging-1.1.3.jar
12:22:31.514 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.fasterxml.jackson.core:jackson-annotations:2.4.0:jackson-annotations.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-annotations/2.4.0/d6a66c7a5f01cf500377bd669507a08cfeba882a/jackson-annotations-2.4.0.jar
12:22:31.514 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.fasterxml.jackson.core:jackson-core:2.4.3:jackson-core.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.fasterxml.jackson.core/jackson-core/2.4.3/4cb3dbb0c2f75b51aa7543c53252989785a0c609/jackson-core-2.4.3.jar
12:22:31.515 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.hamcrest:hamcrest-core:1.1:hamcrest-core.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.hamcrest/hamcrest-core/1.1/860340562250678d1a344907ac75754e259cdb14/hamcrest-core-1.1.jar
12:22:31.515 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.objenesis:objenesis:1.0:objenesis.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.objenesis/objenesis/1.0/9b473564e792c2bdf1449da1f0b1b5bff9805704/objenesis-1.0.jar
12:22:31.516 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.mockito:mockito-core:1.9.5:mockito-core.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.mockito/mockito-core/1.9.5/c3264abeea62c4d2f367e21484fbb40c7e256393/mockito-core-1.9.5.jar
12:22:31.516 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'org.beanshell:bsh:2.0b4:bsh.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/org.beanshell/bsh/2.0b4/a05f0a0feefa8d8467ac80e16e7de071489f0d9c/bsh-2.0b4.jar
12:22:31.516 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.CachingModuleVersionRepository] Found artifact 'com.beust:jcommander:1.27:jcommander.jar' in resolver cache: /home/flo/.gradle/caches/modules-2/files-2.1/com.beust/jcommander/1.27/58c9cbf0f1fa296f93c712f2cf46de50471920f9/jcommander-1.27.jar
12:22:31.677 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache outputFileStates.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/outputFileStates.bin)
12:22:31.682 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache fileHashes.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/fileHashes.bin)
12:22:31.687 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Opening cache fileSnapshots.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/fileSnapshots.bin)
12:22:31.694 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':eclipseClasspath'
12:22:31.695 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseClasspath (Thread[main,5,main]) completed. Took 3.005 secs.
12:22:31.695 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseJdt (Thread[main,5,main]) started.
12:22:31.695 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :eclipseJdt
12:22:31.695 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':eclipseJdt'
12:22:31.695 [DEBUG] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Determining if task ':eclipseJdt' is up-to-date
12:22:31.696 [INFO] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Executing task ':eclipseJdt' (up-to-date check took 0.001 secs) due to:
  Task.upToDateWhen is false.
12:22:31.696 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter] Executing actions for task ':eclipseJdt'.
12:22:31.721 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':eclipseJdt'
12:22:31.721 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseJdt (Thread[main,5,main]) completed. Took 0.026 secs.
12:22:31.721 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseProject (Thread[main,5,main]) started.
12:22:31.721 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :eclipseProject
12:22:31.721 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':eclipseProject'
12:22:31.722 [DEBUG] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Determining if task ':eclipseProject' is up-to-date
12:22:31.722 [INFO] [org.gradle.api.internal.tasks.execution.SkipUpToDateTaskExecuter] Executing task ':eclipseProject' (up-to-date check took 0.0 secs) due to:
  Task.upToDateWhen is false.
12:22:31.722 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter] Executing actions for task ':eclipseProject'.
12:22:31.767 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':eclipseProject'
12:22:31.767 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseProject (Thread[main,5,main]) completed. Took 0.046 secs.
12:22:31.767 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseWtp (Thread[main,5,main]) started.
12:22:31.768 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :eclipseWtp
12:22:31.768 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':eclipseWtp'
12:22:31.768 [INFO] [org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter] Skipping task ':eclipseWtp' as it has no actions.
12:22:31.768 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':eclipseWtp'
12:22:31.768 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :eclipseWtp UP-TO-DATE
12:22:31.769 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipseWtp (Thread[main,5,main]) completed. Took 0.001 secs.
12:22:31.769 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipse (Thread[main,5,main]) started.
12:22:31.769 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :eclipse
12:22:31.769 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':eclipse'
12:22:31.769 [INFO] [org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter] Skipping task ':eclipse' as it has no actions.
12:22:31.769 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':eclipse'
12:22:31.770 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :eclipse (Thread[main,5,main]) completed. Took 0.001 secs.
12:22:31.770 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :setup (Thread[main,5,main]) started.
12:22:31.770 [LIFECYCLE] [class org.gradle.TaskExecutionLogger] :setup
12:22:31.770 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Starting to execute task ':setup'
12:22:31.770 [INFO] [org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter] Skipping task ':setup' as it has no actions.
12:22:31.771 [DEBUG] [org.gradle.api.internal.tasks.execution.ExecuteAtMostOnceTaskExecuter] Finished executing task ':setup'
12:22:31.771 [INFO] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] :setup (Thread[main,5,main]) completed. Took 0.001 secs.
12:22:31.771 [DEBUG] [org.gradle.execution.taskgraph.AbstractTaskPlanExecutor] Task worker [Thread[main,5,main]] finished, busy: 3.109 secs, idle: 0.009 secs
12:22:31.771 [DEBUG] [org.gradle.execution.taskgraph.DefaultTaskGraphExecuter] Timing: Executing the DAG took 3.121 secs
12:22:31.771 [LIFECYCLE] [org.gradle.BuildResultLogger] 
12:22:31.773 [LIFECYCLE] [org.gradle.BuildResultLogger] BUILD SUCCESSFUL
12:22:31.773 [LIFECYCLE] [org.gradle.BuildResultLogger] 
12:22:31.773 [LIFECYCLE] [org.gradle.BuildResultLogger] Total time: 6.843 secs
12:22:31.775 [DEBUG] [org.gradle.api.internal.tasks.compile.daemon.CompilerDaemonManager] Stopping 0 compiler daemon(s).
12:22:31.775 [INFO] [org.gradle.api.internal.tasks.compile.daemon.CompilerDaemonManager] Stopped 0 compiler daemon(s).
12:22:31.777 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Releasing lock on buildscript class cache for build file '/home/flo/workspace/Tools/build.gradle' (/home/flo/.gradle/caches/1.11/scripts/build_50qv936hqeqf60ceghae3hc8o4/ProjectScript/buildscript).
12:22:31.778 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Releasing lock on no_buildscript class cache for build file '/home/flo/workspace/Tools/build.gradle' (/home/flo/.gradle/caches/1.11/scripts/build_50qv936hqeqf60ceghae3hc8o4/ProjectScript/no_buildscript).
12:22:31.781 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache outputFileStates.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/outputFileStates.bin)
12:22:31.781 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache fileSnapshots.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/fileSnapshots.bin)
12:22:31.781 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache taskArtifacts.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/taskArtifacts.bin)
12:22:31.781 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache fileHashes.bin (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts/fileHashes.bin)
12:22:31.781 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Releasing lock on task history cache (/home/flo/workspace/Tools/.gradle/1.11/taskArtifacts).
12:22:31.784 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache module-metadata.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/module-metadata.bin)
12:22:31.784 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache artifact-at-url.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/artifact-at-url.bin)
12:22:31.784 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache artifact-at-repository.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/artifact-at-repository.bin)
12:22:31.784 [DEBUG] [org.gradle.cache.internal.btree.BTreePersistentIndexedCache] Closing cache dynamic-revisions.bin (/home/flo/.gradle/caches/modules-2/metadata-2.2/dynamic-revisions.bin)
12:22:31.785 [DEBUG] [org.gradle.cache.internal.DefaultFileLockManager] Releasing lock on artifact cache (/home/flo/.gradle/caches/modules-2).
12:22:31.786 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.ivyresolve.memcache.InMemoryDependencyMetadataCache] In-memory dependency metadata cache closed. Repos cached: 2, cache instances: 1, modules served from cache: 18, artifacts: 0
12:22:31.788 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.store.CachedStoreFactory] Resolution result cache closed. Cache reads: 2, disk reads: 1 (avg: 0.007 secs, total: 0.007 secs)
12:22:31.788 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.store.CachedStoreFactory] Resolved configuration cache closed. Cache reads: 21, disk reads: 1 (avg: 0.009 secs, total: 0.009 secs)
12:22:31.788 [DEBUG] [org.gradle.api.internal.artifacts.ivyservice.resolveengine.store.ResolutionResultsStoreFactory] Deleted 2 resolution results binary files in 0.001 secs
