import {RoleAssign} from "./role-assign";
import {Credentials} from "./credentials";


export class SignupRequest{
  credentials: Credentials | undefined;
  email: string | undefined;
  role: RoleAssign = RoleAssign.BLOGGER;
}
