/*
 * Copyright 2016-2020 47 Degrees Open Source <https://www.47deg.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package catslib

object ApplyHelpers {
  val intToString: Int => String = _.toString
  val double: Int => Int         = _ * 2
  val addTwo: Int => Int         = _ + 2
  val addArity2                  = (a: Int, b: Int) => a + b
  val addArity3                  = (a: Int, b: Int, c: Int) => a + b + c
}
