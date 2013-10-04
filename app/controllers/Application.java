package controllers;

import models.Project;
import models.Task;
import models.User;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render(User.find.all(),Project.find.all(),Task.find.all()));
    }
  
}
