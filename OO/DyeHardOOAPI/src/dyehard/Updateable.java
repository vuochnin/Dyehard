package dyehard;

import dyehard.Enums.ManagerStateEnum;

// TODO: Auto-generated Javadoc
/**
 * The Interface Updateable.
 */
public interface Updateable {

        /**
         * Update.
         */
        public void update();

        /**
         * Sets the speed.
         *
         * @param factor the new speed
         */
        public void setSpeed(float factor);

        /**
         * Update state.
         *
         * @return the manager state enum
         */
        public ManagerStateEnum updateState();
}
