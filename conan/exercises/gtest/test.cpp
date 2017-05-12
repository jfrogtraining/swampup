// In the test file
#include <gtest/gtest.h>
#include "hellolib.h"

TEST(SalutationTest, Static) {
  EXPECT_EQ(string("Hello World!"), Salutation::greet("World"));
}
