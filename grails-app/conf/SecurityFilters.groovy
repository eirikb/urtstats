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

        accessController(controller:"admin", action:"*") {
            before = {
                accessControl {
                    role("ADMIN")
                }
            }
        }

        createNews(controller:"news", action:"create")  {
            before = {
                accessControl {
                    permission(type:"news", actions:"create")
                }
            }
        }

        saveNews(controller:"news", action:"save")  {
            before = {
                accessControl {
                    permission(type:"news", actions:"save")
                }
            }
        }

        forumGenreEdit(controller:"forumGenre", action:"(create|edit|update|save|delete)") {
            before = {
                accessControl {
                    permission(new org.jsecurity.grails.JsecBasicPermission('forum', 'genre:create'))
                }
            }
        }

        forumTopicEdit(controller:"forumTopic", action:"(create|edit|update|save|delete)") {
            before = {
                accessControl {
                    role("USER")
                }
            }
        }

        forumPostEdit(controller:"forumPost", action:"(create|edit|update|save|delete)") {
            before = {
                accessControl {
                    role("USER")
                }
            }
        }
    }
}
