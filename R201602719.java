package IA1jun23;
import robocode.*;
import java.awt.Color;
import java.lang.Math;
import java.util.Random;
import robocode.util.Utils;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * R201602719 - a robot by Christofer
 */
public class R201602719 extends AdvancedRobot
{
	/**
	 * run: R201602719's default behavior
	 */	
	int Xmax, Xmin, Ymax, Ymin;
	Random random = new Random();

	public void run() {
		setColors(Color.pink,Color.blue,Color.green);
		int margen = 50;
		Xmin = Ymin = margen;
		Xmax = (int)getBattleFieldWidth() - margen;
		Ymax = (int)getBattleFieldHeight() - margen;
		while(true) {	
			setAhead(100000);
			corregirPosicion();
			vigilar();
		}
	}
	public void vigilar(){
        	setTurnRadarRight(45);
	}
	
	public void corregirPosicion(){
		if(paredCercana()){
			back(100);
			setTurnLeft(120);
			waitFor(new TurnCompleteCondition(this));
		}
		mover();
	}
	public boolean estaCerca(int numero1, int numero2, int holgura) {
        int diferencia = Math.abs(numero1 - numero2);
        return diferencia <= holgura;
    }
	public boolean paredCercana(){
		if(estaCerca((int)getX(), Xmin, 30)) return true;
		if (estaCerca((int)getX(), Xmax, 30)) return true;
		if (estaCerca((int)getY(), Ymin, 30)) return true;
		if (estaCerca((int)getY(), Ymax, 30)) return true;
		return false;
	}
	public void mover(){
	    int movimiento = random.nextInt(3);
		switch(movimiento){
			case 0:
				setTurnRight(5);
				waitFor(new TurnCompleteCondition(this));
				break;
			case 1: 
				setTurnLeft(5);
				waitFor(new TurnCompleteCondition(this));
				break;
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		if(e.getDistance() < 700){
			double angulo = e.getBearing();
		    double anguloApuntar = getHeadingRadians() + Math.toRadians(angulo);
		    setTurnGunRightRadians(Utils.normalRelativeAngle(anguloApuntar - getGunHeadingRadians()));
			fire(1);
		}
	}
	public void onHitByBullet(HitByBulletEvent e) {
		setTurnRight(90);
		waitFor(new TurnCompleteCondition(this));
	}
	public void onHitRobot(HitRobotEvent e) {
		if (e.isMyFault()) {
			fire(1);
		}
		corregirPosicion();
	}
	public void onHitWall(HitWallEvent e) {
		turnRight(180);
		ahead(300);
	}
}
