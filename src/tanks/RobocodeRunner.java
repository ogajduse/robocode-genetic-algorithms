package tanks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
//import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

public class RobocodeRunner {

	public static void main(String[] args) throws IOException {

		Config conf = new Config();

		Population pop = new Population();

		//op.loadBest();

		pop.evolve(Config.getIter());


		//pop.writeBest();
	}


}