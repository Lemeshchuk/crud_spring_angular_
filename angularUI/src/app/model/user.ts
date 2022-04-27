import {Role} from "./role";

export class User {
  id: number;
  role : Role;
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  birthday: any;
  age: number;
  confirmPassword: string;
  defaultRolePage: string;
}
