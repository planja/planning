package common

import org.specs2.mutable._


/**
  * Created by ShchykalauM on 08.02.2017.
  */


trait SingleInstanceForTest extends Before {
  def before() {
    import play.api.Play
    if (Play.unsafeApplication == null) Play.start(SpecificationForTest)
  }
}
