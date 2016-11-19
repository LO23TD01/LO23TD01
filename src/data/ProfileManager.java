package data;

public class ProfileManager {
    private boolean hasProfileOpened;
    private Profile currentProfile;
    
    public boolean getHasProfileOpened() {
        return hasProfileOpened;
    }
    
    //TODO: assuming public get/private set
    private void setHasProfileOpened(boolean value) {
        if (hasProfileOpened != value) {
            //TODO: trigger event
            hasProfileOpened = value;
        }
    }
    
    public Profile getCurrentProfile() {
        return currentProfile;
    }
    
    //TODO: assuming public get/private set
    private void setCurrentProfile(Profile value) {
        if (currentProfile != value) {
            //TODO: trigger event
            currentProfile = value;
            setHasProfileOpened(currentProfile != null);
        }
    }
    
    public Profile createProfile(Object... args) {
        return null;
    }
    
    public boolean checkPassword(String login, String password) {
        return false;
    }
    
    public Profile logIn(String login, String password) {
        //TODO: setCurrentProfile()
        return null;
    }
    
    public void logOut(Profile p) {
        if (p == getCurrentProfile()) {
            setCurrentProfile(null);
        }
        
        //TODO: logout routine goes here
    }
    
  public Profile modifyProfile(Profile p, Object... args) {
      //TODO: Is Profile a mutable class?
      //If not, consider Profile newP = new Profile(p); return newP;
      
      return p;
  }
}
