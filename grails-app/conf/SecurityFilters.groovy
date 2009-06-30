/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class SecurityFilters {

    def filters = {

        auth(controller: "*", action: "*") {
            before = {
                if (controllerName == null ||
                    controllerName == "team" ||
                    controllerName == "game") {
                    return true
                }
                accessControl {
                    true
                }
            }
        }

        playerEditing(controller: "player", action: "(create|edit|save|update|delete)") {
            before = {
                accessControl {
                    role("Administrator")
                }
            }
        }
        
        playerShow(controller: "player", action: "show") {
            before = {
                accessControl {
                    role("Administrator") || role("User")
                }
            }
        }
    }
}
