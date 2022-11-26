package fudus.api.model

object Ops {
  implicit class UserRoleOps0(userRole: UserRole.Type) {
    def asString: String = userRole match {
      case UserRole.Client => "Client"
      case UserRole.Admin  => "Admin"
      case _               => "???"
    }
  }

  implicit class StringOps0(str: String) {
    def asUserRole: UserRole.Type = str match {
      case "Client" => UserRole.Client
      case "Admin"  => UserRole.Admin
    }
  }
}
