package guhFitNesse;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;

import fitnesse.junit.FitNesseRunner.ConfigFile;
import fitnesse.junit.FitNesseRunner.FitnesseDir;
import fitnesse.junit.FitNesseRunner.OutputDir;
import fitnesse.junit.FitNesseSuite;
import fitnesse.junit.FitNesseSuite.Name;

@RunWith(FitNesseSuite.class)
@Name("IdmFitNesse.BasicSetup")
@FitnesseDir("./dbfit")
@OutputDir("tmp")
@ConfigFile("plugins.properties")
public class ConnectionSuite {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
