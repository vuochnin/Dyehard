import Engine.Vector2;
import dyehard.Player.Hero;
import dyehard.Util.Timer;
import dyehard.Weapons.Bullet;
import dyehard.Weapons.Weapon;

public class Lab5WepCorrect extends Weapon {

	public Lab5WepCorrect(Hero hero) {
		super(hero);
		bulletSpeed += 5f;
		bulletSize += 5f;
		timer = new Timer(200);	// in milliseconds
	}
	
	 @Override
	public void fire() {
		 if (timer.isDone()) {
			 Bullet bullet = new Bullet(hero);
			 bullet.center = new Vector2(hero.center);
			 bullet.size.set(bulletSize, bulletSize);
			 bullet.velocity = new Vector2(bulletSpeed, bulletSpeed / 2);
			 bullet.color = hero.getColor();

			 bullet = new Bullet(hero);
			 bullet.center = new Vector2(hero.center);
			 bullet.size.set(bulletSize, bulletSize);
			 bullet.velocity = new Vector2(bulletSpeed, -bulletSpeed / 2);
			 bullet.color = hero.getColor();

			 bullet = new Bullet(hero);
			 bullet.center = new Vector2(hero.center);
			 bullet.size.set(bulletSize, bulletSize);
			 bullet.velocity = new Vector2(bulletSpeed, 0f);
			 bullet.color = hero.getColor();
	     }
		 super.fire();   
	}

}
