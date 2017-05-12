#include "hellolib.h"

string Salutation::greet(const string& name) {
  ostringstream s;
  s << "Hello " << name << "!";
  return s.str();
}