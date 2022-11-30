package fudus.api.model

object Ops {
  implicit class UserRoleOps0(userRole: CustomerRole.Type) {
    def asString: String = userRole match {
      case CustomerRole.Client => "Client"
      case CustomerRole.Admin  => "Admin"
      case _                   => "???"
    }
  }

  implicit class StringOps0(str: String) {
    def asUserRole: CustomerRole.Type = str match {
      case "Client" => CustomerRole.Client
      case "Admin"  => CustomerRole.Admin
    }
  }

  implicit class UserOps0(user: Customer) {
    def hasAdminRole: Boolean = user.role == CustomerRole.Admin
    def hasClientRole: Boolean = user.role == CustomerRole.Client
  }
}
