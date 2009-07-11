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


        playerEditing(controller:"player", action:"(create|edit|update|save|delete)") {
            before = {
                accessControl {
                    role("ADMIN")
                }
            }
        }

        accessController(controller:"access", action:"*") {
            before = {
                accessControl {
                    role("ADMIN")
                }
            }
        }

        newsEdit(controller:"news", action:"(create|edit|update|save|delete)")  {
            before = {
                accessControl {
                    permission(new org.jsecurity.authz.permission.WildcardPermission('news:*'))
                }
            }
        }
    }
}
