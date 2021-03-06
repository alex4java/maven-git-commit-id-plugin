package pl.project13.maven.git.build;

import pl.project13.maven.git.GitCommitPropertyConstant;
import pl.project13.maven.git.log.LoggerBridge;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Properties;

public class TravisBuildServerData extends BuildServerDataProvider {

  TravisBuildServerData(LoggerBridge log, @Nonnull Map<String, String> env) {
    super(log, env);
  }

  /**
   * @see <a href=https://docs.travis-ci.com/user/environment-variables/#Default-Environment-Variables>Travis</a>
   */
  public static boolean isActiveServer(@Nonnull Map<String, String> env) {
    return env.containsKey("TRAVIS");
  }

  @Override
  void loadBuildNumber(@Nonnull Properties properties) {
    String buildNumber = env.get("TRAVIS_BUILD_NUMBER");
    String uniqueBuildNumber = env.get("TRAVIS_BUILD_ID");

    put(properties, GitCommitPropertyConstant.BUILD_NUMBER, buildNumber == null ? "" : buildNumber);
    put(properties, GitCommitPropertyConstant.BUILD_NUMBER_UNIQUE, uniqueBuildNumber == null ? "" : uniqueBuildNumber);
  }

  @Override
  public String getBuildBranch() {
    String environmentBasedBranch = env.get("TRAVIS_BRANCH");
    log.info("Using environment variable based branch name. TRAVIS_BRANCH = {}", environmentBasedBranch);
    return environmentBasedBranch;
  }
}
